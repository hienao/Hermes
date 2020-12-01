package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask
import com.hienao.hermes.StartUpTask
import kotlin.concurrent.thread

class FourTask : AndroidStartUpTask<Boolean> {
    override fun callOnUIThread(): Boolean {
        return false
    }

    override fun dependences(): MutableList<Class<out StartUpTask<*>>> {
        var depTaskList= mutableListOf<Class<out StartUpTask<*>>>()
        depTaskList.add(FirstTask::class.java)
        return super.dependences()
    }
    override suspend fun call(context: Context?):Boolean {
        Thread.sleep(4000)
        return true
    }
}