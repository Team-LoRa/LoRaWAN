package com.teamlora.loralibrary

import android.util.Log


//example functions

fun LogcatStart(){

    Log.d("myTag", "LoraWAN Libraries are working.");
}

fun LogcatConnection(){
    val connection = 60
    Log.d("myTag", "Connection spped is : " + connection + " Bytes/Message");
}