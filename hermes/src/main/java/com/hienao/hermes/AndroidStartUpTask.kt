package com.hienao.hermes

import android.content.Context

interface AndroidStartUpTask<T> : StartUpTask<T> {
    /**
     * 是否从UI线程启动
     */
    fun callOnUIThread() = true

    /**
     * 执行任务内容
     */
    suspend fun call(context: Context?):Boolean

    /**
     * 前置依赖任务
     */
    fun dependences():MutableList<Class<out StartUpTask<*>>> = mutableListOf()
}