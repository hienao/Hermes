package com.hienao.hermes

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskExecutor {
    companion object {
        fun executeTask(task: AndroidStartUpTask<*>, context: Context?, finishTaskMap: MutableMap<AndroidStartUpTask<*>, Boolean>) {
            var startTime = System.currentTimeMillis()
            if (task.callOnUIThread()) {
                GlobalScope.launch(Dispatchers.Main) {
                    var threadId = Thread.currentThread().id
                    var result = task.call(context)
                    launch(Dispatchers.Main) {
                        Log.i("TaskExecutor", "Task[ ${task.javaClass.name} ]  : 执行线程id：${threadId}，结果：[ $result ] 执行时间：${System.currentTimeMillis() - startTime}，开始时间：${startTime}")
                        finishTaskMap[task] = result
                    }
                }

            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    var threadId = Thread.currentThread().id
                    var result = task.call(context)
                    launch(Dispatchers.Main) {
                        Log.i("TaskExecutor", "Task[ ${task.javaClass.name} ]  : 执行线程id：${threadId}，结果：[ $result ] 执行时间：${System.currentTimeMillis() - startTime}，开始时间：${startTime}")
                        finishTaskMap[task] = result
                    }
                }
            }
        }
    }

}