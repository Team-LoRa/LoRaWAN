package com.example.lorawanapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.teamlora.loralibrary.*
import java.io.DataOutputStream
import java.net.Socket
import java.net.URL
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val IP = findViewById<EditText>(R.id.message)



        //Call to the Logcat saying the app has started
        LogcatStart()


        button.setOnClickListener {

             if(ping(IP.text.toString()))
            {
                Toast.makeText(this, "connected", Toast.LENGTH_LONG).show()
            }

            //Check Internet Connection

            else
            {
                Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show()
            }

        }


}
}
