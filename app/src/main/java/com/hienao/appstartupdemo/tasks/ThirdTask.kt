package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask
import com.hienao.hermes.StartUpTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ThirdTask : AndroidStartUpTask<Boolean> {
    override fun callOnUIThread(): Boolean {
        return false
    }

    override fun dependences(): MutableList<Class<out StartUpTask<*>>> {
        var depTaskList= mutableListOf<Class<out StartUpTask<*>>>()
        depTaskList.add(SecondTask::class.java)
        return super.dependences()
    }
    override suspend fun call(context: Context?):Boolean {
        Thread.sleep(3000)
        GlobalScope.launch (Dispatchers.Main){
            Log.i("TaskExecutor","内部切换线程，当前Task：【${this@ThirdTask.javaClass.name}】当前线程Id：【${Thread.currentThread().id}】")
        }
        return true
    }
}