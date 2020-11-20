package com.hienao.hermes

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskExecutor {
    companion object{
        fun executeTask(task:AndroidStartUpTask<*>,context: Context,finishTaskMap:MutableMap<AndroidStartUpTask<*>,Boolean> ){
            if (task.callOnUIThread()){
                GlobalScope.launch (Dispatchers.Main){
                    task.call(context)
                    finishTaskMap[task]=true
                }
            }else{
                task.call(context)
            }
        }
    }
}