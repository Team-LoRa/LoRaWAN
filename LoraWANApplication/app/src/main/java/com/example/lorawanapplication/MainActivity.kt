package com.example.lorawanapplication

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.teamlora.loralibrary.LoRaMessenger
import com.teamlora.loralibrary.LogcatStart
import com.teamlora.loralibrary.oldSendLoRaMessage
import com.teamlora.loralibrary.ping
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sendButton = findViewById<Button>(R.id.button)
        val connectButton = findViewById<Button>(R.id.Connectbutton)
        val message = findViewById<EditText>(R.id.message)
        val appSpinner = findViewById<Spinner>(R.id.appSpinner)
        val roomSpinner = findViewById<Spinner>(R.id.roomSpinner)



        //val threadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        //StrictMode.setThreadPolicy(threadPolicy)

        //Call to the Logcat saying the app has started
        LogcatStart()

       sendButton.setOnClickListener {

        Log.d("myTag","the app spinner index is now on: " + appSpinner.selectedItem.toString())

        // Initialize the LoRaMessenger object,   appSpinner example is TempControl
        val messenger = LoRaMessenger(appSpinner.selectedItem.toString())

        /* TODO: Improve the implementation below so that the developer does not need to call
        readEncodingTable() themselves */

        // Read the encoding table from assets
        val jsonString: String =
            application.assets.open("encoding_table.json").bufferedReader().use {
             it.readText()
            }

        // Pass the encoding table to the messenger
        messenger.readEncodingTable(jsonString)

        Log.d("myTag","the room spinner index is now on: " + roomSpinner.selectedItem.toString())

        //roomSpinner example is dining room
        val parameters: Array<Any> = arrayOf(15420, roomSpinner.selectedItem.toString())

        //message example is tempUp
        messenger.sendLoRaMessage("tempUp", parameters)

}

    //Check Internet Connection
    connectButton.setOnClickListener {

        if(ping())
        {
            Toast.makeText(this, "The connection is available", Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this, "The connection is not available", Toast.LENGTH_LONG).show()
        }

    }

}
}
