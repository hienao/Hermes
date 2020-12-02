package com.hienao.hermes

interface TaskResultCallback {
    fun finishTask(task: AndroidStartUpTask<*>,result:Boolean)
}