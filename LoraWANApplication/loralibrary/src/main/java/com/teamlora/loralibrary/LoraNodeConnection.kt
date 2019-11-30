package com.teamlora.loralibrary

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.net.Proxy.getHost
import android.util.Log
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

fun ping(url: URL, ctx: Context): Ping {

    Log.d("myTag", ":running ping test")

    val r = Ping()
    if (isNetworkConnected(ctx)) {
        r.net = getNetworkType(ctx)

        Log.d("myTag", "net: " + r.net)
        Log.d("myTag", "host: " + r.host)
        Log.d("myTag", "ip: " + r.ip)
        Log.d("myTag", "dns: " + r.dns)
        Log.d("myTag", "cnt: " + r.cnt)

        try {
            val hostAddress: String
            val start = System.currentTimeMillis()
            hostAddress = InetAddress.getByName(url.getHost()).getHostAddress()
            val dnsResolved = System.currentTimeMillis()
            val socket = Socket(hostAddress, url.getPort())
            socket.close()
            val probeFinish = System.currentTimeMillis()
            r.dns = (dnsResolved - start).toInt()
            r.cnt = (probeFinish - dnsResolved).toInt()
            r.host = url.getHost()
            r.ip = hostAddress

        } catch (ex: Exception) {
            Log.e("myTag", "Unable to Ping host")
        }

    }
    return r
}



fun isNetworkConnected(context: Context): Boolean {
    Log.d("myTag", ":checking if network is connected")
    val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun getNetworkType(context: Context): String? {
    Log.d("myTag", ":checking network type")
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork?.typeName
}




fun executeCommand(): Boolean {
    Log.d("myTag", ":re-testing connection")

    val runtime = Runtime.getRuntime()
    try {
        val mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val mExitValue = mIpAddrProcess.waitFor()

        //println(" mExitValue $mExitValue")
        Log.d("myTag", "ping time: " + mExitValue);

        if (mExitValue == 0) {
            Log.d("myTag", "Connected to Googles IP")
            return true
        }
        else {
            Log.e("myTag", "Could not connect to Googles IP")
            return false
        }

    } catch (ignore: InterruptedException) {
        ignore.printStackTrace()

        //println(" Exception:$ignore")

        Log.d("myTag", "Exception : " + ignore)
    } catch (e: IOException) {
        e.printStackTrace()

        //println(" Exception:$e")
        Log.d("myTag", "Exception : " + e)

    }

    return false
}