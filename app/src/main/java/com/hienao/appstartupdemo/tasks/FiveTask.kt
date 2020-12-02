package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask
import com.hienao.hermes.StartUpTask

class FiveTask : AndroidStartUpTask<Boolean> {

    override fun blockUIThread(): Boolean {
        return true
    }

    override suspend fun call(context: Context?): Boolean {
        return true
    }
    override fun dependences(): MutableList<Class<out StartUpTask<*>>> {
        var depTaskList= mutableListOf<Class<out StartUpTask<*>>>()
        depTaskList.add(ThirdTask::class.java)
        return depTaskList
    }
}