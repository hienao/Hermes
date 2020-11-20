package com.hienao.appstartupdemo.tasks

import android.content.Context
import android.util.Log
import com.hienao.hermes.AndroidStartUpTask

class SecondTask : AndroidStartUpTask<Boolean> {
    override fun call(context: Context) {
        Log.i("TEST","call:"+this.javaClass.name)
    }
}