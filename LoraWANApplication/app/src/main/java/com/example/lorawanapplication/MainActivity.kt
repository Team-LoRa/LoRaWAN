package com.example.lorawanapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamlora.loralibrary.*
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Call to the Logcat saying the app has started
        LogcatStart()

        //check connection again
        ping(URL("https://www.google.com:443/"), this);

        //Check Internet Connection
        executeCommand()

}
}
