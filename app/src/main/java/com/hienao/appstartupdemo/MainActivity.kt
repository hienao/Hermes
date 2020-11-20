package com.hienao.appstartupdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hienao.appstartupdemo.tasks.FirstTask
import com.hienao.appstartupdemo.tasks.SecondTask
import com.hienao.hermes.TaskManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TaskManager.Builder
            .addTask(FirstTask())
            .addTask(SecondTask())
            .build(application)
    }
}