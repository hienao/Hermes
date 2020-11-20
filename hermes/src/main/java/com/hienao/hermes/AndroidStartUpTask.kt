package com.hienao.hermes

import android.content.Context

interface AndroidStartUpTask<T> : StartUpTask<T> {
    fun callOnUIThread() = true
    fun blockUIThread() = false
    fun call(context: Context):T
    fun dependences():MutableList<Class<out StartUpTask<*>>> = mutableListOf()
}