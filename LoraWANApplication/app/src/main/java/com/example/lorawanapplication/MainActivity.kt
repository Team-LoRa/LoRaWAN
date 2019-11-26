package com.example.lorawanapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import  com.teamlora.loralibrary.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Call to the Logcat saying the app has started
        LogcatStart()

        //Another example of a function in use
        LogcatConnection()

        ExampleFunction()

    }
}
