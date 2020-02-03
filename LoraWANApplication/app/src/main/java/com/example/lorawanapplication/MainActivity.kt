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
        val message = findViewById<EditText>(R.id.message)



        //Call to the Logcat saying the app has started
        LogcatStart()


        button.setOnClickListener {

            connect(URL("https://www.google.com:443/"), this, message.getText().toString());

            //Check Internet Connection


            Toast.makeText(this, message.getText().toString(), Toast.LENGTH_LONG).show()

        }


}
}
