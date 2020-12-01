package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask

class FirstTask : AndroidStartUpTask<Boolean> {
    override suspend fun call(context: Context?):Boolean{
        return true
    }
}