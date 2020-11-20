package com.hienao.hermes

import android.content.Context

class TaskManager private constructor(builder: Builder) {
    private var taskList: MutableList<AndroidStartUpTask<*>> = mutableListOf()
    private var context: Context? = null
    private var finishTaskMap:MutableMap<AndroidStartUpTask<*>,Boolean> = mutableMapOf()
    init {
        taskList.addAll(builder.taskList)
        this.context = builder.context
    }

    companion object Builder {
        private var taskList: MutableList<AndroidStartUpTask<*>> = mutableListOf()
        private var context: Context? = null
        fun addTask(task: AndroidStartUpTask<*>): Builder {
            taskList.add(task)
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
    }
}