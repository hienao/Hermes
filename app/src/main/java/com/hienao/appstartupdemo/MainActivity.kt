package com.hienao.appstartupdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hienao.appstartupdemo.tasks.*
import com.hienao.hermes.FinishAllTaskCallback
import com.hienao.hermes.TaskConstant
import com.hienao.hermes.TaskManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var currentTime=System.currentTimeMillis()
        Log.i(TaskConstant.TASK_TAG, "Task列表开始执行")
        TaskManager.Builder
                .addTask(ThirdTask())
                .addTask(FourTask())
                .addTask(SecondTask())
                .addTask(FirstTask())
                .addTask(FiveTask())
                .setFinishCallBack(object :FinishAllTaskCallback{
                    override fun finish() {
                        Log.i(TaskConstant.TASK_TAG, "Task列表执行完成,用时：${System.currentTimeMillis()-currentTime}")
                    }

                })
                .build(application)
                .start()
    }
}