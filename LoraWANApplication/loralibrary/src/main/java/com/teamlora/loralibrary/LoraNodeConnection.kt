package com.teamlora.loralibrary

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.net.Proxy.getHost
import android.renderscript.Sampler
import android.util.Log
import java.io.DataOutputStream
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.URL


class Ping {
    var net: String? = "NO_CONNECTION"
    var host = "google-public-dns-a.google.com"
    var ip = "8.8.8.8"
    var dns = Integer.MAX_VALUE
    var cnt = Integer.MAX_VALUE
}

fun connect(url: URL, ctx: Context, message: String)
{
    Log.d("myTag", "first run")



    try
    {
    val ipAddress: InetAddress?

    val buffer = ByteArray(4)

        //Assign Ip Address 127.0.0.1
    buffer.set(0,127)
    buffer.set(1,0)
    buffer.set(2,0)
    buffer.set(3,1)
        Log.d("myTag", "went here1")
    ipAddress = InetAddress.getByAddress(buffer)
     Log.d("myTag", "went here2")
    val s =  Socket(ipAddress,80)

    val outputStream1 = DataOutputStream(s.getOutputStream())
     outputStream1.writeUTF(message)
      outputStream1.close()

        Log.d("myTag", "it's connected bitch")



    } catch (ex: Exception)
        {
            Log.e("myTag", "Unable to Ping host")
        }



}








