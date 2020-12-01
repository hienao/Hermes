package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask
import kotlin.concurrent.thread

class SecondTask : AndroidStartUpTask<Boolean> {
    override fun callOnUIThread(): Boolean {
        return false
    }
    override suspend fun call(context: Context?):Boolean {
        Thread.sleep(5000)
        return true
    }
}