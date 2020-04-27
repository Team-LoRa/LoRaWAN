package com.example.lorawanapplication

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.teamlora.loralibrary.LoRaMessenger
import com.teamlora.loralibrary.LogcatStart
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val longitude = findViewById<EditText>(R.id.enterLong)
        val latitude = findViewById<EditText>(R.id.enterLat)
        val mnc = findViewById<EditText>(R.id.enterMNC)
        val mcc = findViewById<EditText>(R.id.enterMCC)
        val lcl = findViewById<EditText>(R.id.enterLAC)
        val cellID = findViewById<EditText>(R.id.enterCellID)

        val sendButton = findViewById<Button>(R.id.button)

        sendButton.setOnClickListener {
            /* TODO: Improve the implementation below so that the developer does not need to call readEncodingTable() themselves */

            // Read the encoding table from assets
            val jsonString: String =
                application.assets.open("encoding_table.json").bufferedReader().use {
                    it.readText()
                }

            // Pass the encoding table to the messenger
            val messenger = LoRaMessenger("OpenCellID", jsonString )

            //roomSpinner example is dining room (double lat, double lon, int mcc, int mnc, int lac, int cellID)
            val parameters: Array<Any> = arrayOf(latitude.text.toString().toDouble(),
                                                longitude.text.toString().toDouble(),
                                                mcc.text.toString().toInt(),
                                                mnc.text.toString().toInt(),
                                                lcl.text.toString().toInt(),
                                                cellID.text.toString().toInt())

            //message example is tempUp
            messenger.sendLoRaMessage("measure/add", parameters )

        }

    }

}
