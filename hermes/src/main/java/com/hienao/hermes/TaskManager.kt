package com.hienao.hermes

import android.content.Context
import android.util.Log
import com.hienao.hermes.TaskConstant.TASK_TAG


class TaskManager private constructor(builder: Builder) {

    /**
     * 尚未开始执行的任务列表
     */
    private var remainTaskMap: MutableMap<String, AndroidStartUpTask<*>> = mutableMapOf()

    /**
     * UI阻塞任务全部完成回调，此时可以继续执行任务
     */
    private var finishAllTaskCallback: FinishAllTaskCallback? = null

    /**
     * 阻塞ui线程的任务的列表
     */
    private var blockTaskMap: MutableMap<String, Boolean> = mutableMapOf()
    private var context: Context? = null
    private var totalCount = 0;

    /**
     * 已执行完成的任务列表
     */
    private var finishTaskMap: MutableMap<String, Boolean> = mutableMapOf()

    init {
        for (task in builder.taskList) {
            remainTaskMap[task.javaClass.name] = task
            if (task.blockUIThread()) {
                blockTaskMap[task.javaClass.name] = false
            }
        }
        totalCount = remainTaskMap.size
        this.context = builder.context
        this.finishAllTaskCallback = builder.finishAllTaskCallback
    }

    companion object Builder {
        private var taskList: MutableList<AndroidStartUpTask<*>> = mutableListOf()
        private var context: Context? = null
        private var finishAllTaskCallback: FinishAllTaskCallback? = null
        fun addTask(task: AndroidStartUpTask<*>): Builder {
            taskList.add(task)
            return this
        }

        fun setFinishCallBack(finishCallBack: FinishAllTaskCallback?): Builder {
            finishAllTaskCallback = finishCallBack
            return this
        }

        fun build(context: Context?): TaskManager {
            this.context = context
            return TaskManager(this)
        }
    }

    fun start() {
        //1.把列表中的任务及依赖绘制有向无环图，进行拓扑排序，找出执行顺序放入执行队列
        //2.从执行队列中获取到排序后的任务，进行执行
        if (blockTaskMap.isEmpty()){
            finishAllTaskCallback?.finish()
        }
        getTasksAndExec()
    }

    private fun getTasksAndExec() {

        var todoTasks = getNeedExecTasks()
        for (task in todoTasks) {
            remainTaskMap.remove(task.javaClass.name)
            TaskExecutor.executeTask(task, this@TaskManager.context, object : TaskResultCallback {
                override fun finishTask(task: AndroidStartUpTask<*>, result: Boolean) {
                    if (!result) {
                        throw RuntimeException("${task.javaClass.name}执行失败，请检查")
                    }
                    if (task.blockUIThread()) {
                        blockTaskMap.remove(task.javaClass.name)
                        if (blockTaskMap.isEmpty()) {
                            finishAllTaskCallback?.finish()
                        }
                    }
                    finishTaskMap[task.javaClass.name] = result

                    getTasksAndExec()
                }
            })
        }
    }

    private fun getNeedExecTasks(): MutableList<AndroidStartUpTask<*>> {
        var tasks = mutableListOf<AndroidStartUpTask<*>>()
        for ((key, value) in remainTaskMap) {
            //是否需要执行，条件为所有依赖任务均已完成
            var needExec = true
            for (taskClass in value.dependences()) {
                if (true != finishTaskMap[taskClass.name]) {
                    needExec = false
                }
            }
            if (needExec) {
                tasks.add(value)
            }
        }
        return tasks
    }

}