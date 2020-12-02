package com.hienao.hermes

import android.content.Context
import android.util.Log
import com.hienao.hermes.TaskConstant.TASK_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaskExecutor {
    companion object {
        fun executeTask(task: AndroidStartUpTask<*>, context: Context?, callback: TaskResultCallback) {
            var startTime = System.currentTimeMillis()
            Log.i(TASK_TAG, "Task[ ${task.javaClass.name} ] 开始执行")
            if (task.callOnUIThread()) {
                GlobalScope.launch(Dispatchers.Main) {
                    var threadId = Thread.currentThread().id
                    var result = task.call(context)
                    launch(Dispatchers.Main) {
                        var timeStr=SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date(startTime))
                        Log.i(TASK_TAG, "Task[ ${task.javaClass.name} ]  : 执行线程id：${threadId}，结果：[ $result ] 执行时间：${System.currentTimeMillis() - startTime}，开始时间：${timeStr}")
                        callback.finishTask(task, result)
                    }
                }

            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    var threadId = Thread.currentThread().id
                    var result = task.call(context)
                    launch(Dispatchers.Main) {
                        var timeStr=SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date(startTime))
                        Log.i(TASK_TAG, "Task[ ${task.javaClass.name} ]  : 执行线程id：${threadId}，结果：[ $result ] 执行时间：${System.currentTimeMillis() - startTime}，开始时间：${timeStr}")
                        callback.finishTask(task, result)
                    }
                }
            }
        }
    }

}