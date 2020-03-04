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
import java.io.PrintWriter
import java.net.*
import java.nio.channels.SocketChannel
import java.nio.charset.Charset
import java.util.*
import kotlin.*

class Ping {
    var net: String? = "NO_CONNECTION"
    var host = "google-public-dns-a.google.com"
    var ip = "8.8.8.8"
    var dns = Integer.MAX_VALUE
    var cnt = Integer.MAX_VALUE
}

fun ping(ipAdress: String): Boolean {
    Log.d("myTag", "current message ip: "+ ipAdress)
    
    //The ip you are trying to connect
    val ipAdressss = "192.168.0.102"
    val runtime = Runtime.getRuntime()


    try {
        
        val mIpAddrProcess = runtime.exec("/system/bin/ping -c 1" + ipAdress)
        val ExitValue = mIpAddrProcess.waitFor()
        Log.d("myTag", "mExitValue is: " + ExitValue)

        Log.d("myTag", "current message ip: "+ ipAdress)
        
        if(ExitValue == 0)
        {
            return true
        }
        else
        {
            return false
        }

    } catch (ex: Exception) {
        Log.e("myTag", "Unable to Ping host")
        return false
    }
}


fun SendLoRaMessage(apiName: String, par: String): Boolean
{
    try {

        val pw: PrintWriter

        val s: Socket

        val buffer = ByteArray(4)

        //Assign Ip Address 192.168.100.102
        buffer.set(0, 192.toByte())
        buffer.set(1, 168.toByte())
        buffer.set(2, 1.toByte())
        buffer.set(3, 102.toByte())
        Log.d("myTag","the url host is: " )
        Log.d("myTag", "went here1")

        val ipAddress = InetAddress.getByAddress(buffer)


        Log.d("myTag", "went here2")

        Log.d("myTag", "the ip address is: " + ipAddress)

        Log.d("myTag", "went here3")

        s = Socket(ipAddress, 2080)
        
        Log.d("myTag", "went here4")
        
        /*
        
        pw = PrintWriter(s.getOutputStream())
        pw.write("helloooo")
        pw.flush()
        pw.close()
        

        */
        
        return true

    } catch (ex: Exception) {
        Log.e("myTag", "Unable to Ping host")
        return false
    }

}

fun ReadEncodingTable()
{

}

fun encodingFromTable()
{

}
