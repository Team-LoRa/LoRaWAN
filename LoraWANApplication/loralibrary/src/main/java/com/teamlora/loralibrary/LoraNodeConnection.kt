package com.teamlora.loralibrary

import android.os.Handler
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Double.doubleToLongBits
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer
import kotlin.math.pow


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



fun oldSendLoRaMessage(): Boolean
{
    val buffer = ByteArray(4)

    //Assign Ip Address 192.168.1.101
    buffer.set(0, 192.toByte())
    buffer.set(1, 168.toByte())
    buffer.set(2, 1.toByte())
    buffer.set(3, 101.toByte())
    Log.d("myTag","the url host is: " )

    val ipAddress = InetAddress.getByAddress(buffer)

    Log.d("myTag", "the ip address is: " + ipAddress)

    val clientSocket : Socket = Socket(ipAddress, 2080)

    clientSocket.outputStream.write("Hello from the client!".toByteArray())
    clientSocket.close()

    return false
}


class LoRaMessenger( val appName: String ) {

    val FLOAT_BYTE_LENGTH : Int = 4
    val DOUBLE_BYTE_LENGTH : Int = 8

    private var encodingTable : JSONObject? = null

    private var encodedMessage : ByteArray = ByteArray( 0 )

    fun sendLoRaMessage(apiName: String, parameters: Array<Any>)
    {
        Log.d("myTag", "sendLoRaMessage started" )
        // Append the byte for the appName to the array
        // Use !! to assert that encodingTable is not null
        val appTable : JSONObject = encodingTable!!.getJSONObject( appName )

        encodedMessage += appTable.getString( "byte_code" ).toInt().toByte()

        // Append the byte for the apiName to the array
        val apiTable : JSONObject = appTable.getJSONObject( apiName )

        encodedMessage += apiTable.getString( "byte_code" ).toInt().toByte()

        Log.d("myTag", encodedMessage[0].toString() )
        Log.d("myTag", encodedMessage[1].toString() )

        // Start tracking the current byte index to handle multi-byte parameters
        var byteIndex : Int = 2

        var paramIndex : Int = 0

        val paramArray : JSONArray = apiTable.getJSONArray( "params" )

        // Iterate through the parameters
        for( parameter in parameters ) {
            // Access the parameter's table
            val paramTable : JSONObject = paramArray[ paramIndex ] as JSONObject

            var paramValues = paramTable.get( "values" )

            // Switch based on the parameter's type
            if ( paramValues is JSONObject ) {
                Log.d("myTag", "Encoding value" )
                // Encode the parameter based on its value map and append it to byteArray
                encodedMessage += paramValues.getString( parameter.toString() ).toInt().toByte()

            }
            else if ( paramValues == "int-param" ) {
                Log.d("myTag","Encoding integer" )
                // Make sure the parameter is an Int
                if( parameter is Int ) {

                    val two : Double = 2.0
                    val mask : Int = 0xFF // binary 1111 1111
                    var paramLength : Int = paramTable.getString( "length" ).toInt()

                    // Make sure that the passed value can be stored within the given number of bytes
                    if( parameter < two.pow( paramLength * 8 ).toDouble() )  {

                        // Convert the integer to an array of bytes

                        // Start with an empty byte array
                        var intByteArray : ByteArray = ByteArray( 0 )

                        // Take the interger, 8 bits at a time, and append it to the byte array
                        var _parameter : Int = parameter
                        for( i in 0 until paramLength ) {
                            intByteArray +=_parameter.and( mask ).toByte()
                            _parameter = _parameter.shr( 8 )
                        }

                        // Reverse the byte array to maintain big endian order
                        intByteArray.reverse()

                        // Append the integer's bytes to the message
                        encodedMessage += intByteArray

                        byteIndex += paramLength
                    }

                }

            }
            else if ( paramValues == "float-param" ){
                Log.d("myTag", "Encoding float" )
                // Make sure the parameter is a Float
                if( parameter is Float ) {

                    val mask : Int = 0xFF // binary 1111 1111

                    // Convert the floating point number to an integer that represents the same
                    // raw bits
                    val int = parameter.toRawBits()
                    Log.d("myTag", "Value as a long" )
                    Log.d("myTag", int.toString() )

                    var floatByteArray : ByteArray = ByteArray( 0 )

                    // Take the integer, 8 bits at a time, and append it to the byte array
                    var _int : Int = int
                    for( i in 0 until FLOAT_BYTE_LENGTH ) {
                        floatByteArray += _int.and( mask ).toByte()
                        _int = _int.shr( 8 )
                    }

                    // Reverse the byte array to maintain big endian order
                    floatByteArray.reverse()

                    // Append the float's bytes to the message
                    encodedMessage += floatByteArray

                    byteIndex += FLOAT_BYTE_LENGTH
                }

            }
            else if ( paramValues == "double-param" ){
                Log.d("myTag", "Encoding double" )
                // Make sure the parameter is a Double
                if( parameter is Double ) {

                    val mask : Long = 0xFF // binary 1111 1111

                    // Convert the double precision floating point number to a long that represents
                    // the same raw bits
                    val long = parameter.toRawBits()
                    Log.d("myTag", "Value as a long" )
                    Log.d("myTag", long.toString() )

                    var doubleByteArray : ByteArray = ByteArray( 0 )

                    // Take the long, 8 bits at a time, and append it to the byte array
                    var _long : Long = long
                    for( i in 0 until DOUBLE_BYTE_LENGTH ) {
                        doubleByteArray += _long.and( mask ).toByte()
                        _long = _long.shr( 8 )
                    }

                    // Reverse the byte array to maintain big endian order
                    doubleByteArray.reverse()

                    // Append the double's bytes to the message
                    encodedMessage += doubleByteArray

                    byteIndex += DOUBLE_BYTE_LENGTH
                }

            }
            else if ( paramValues == "char-param" ){
                Log.d("myTag", "Encoding char" )
                // TODO: Implement character encoding

            }
            else {
                Log.d("myTag", "Something went wrong" )
            }

            paramIndex += 1
        }

        Log.d("myTag", "Resulting byte array" )
        for( byte in encodedMessage ) {
            Log.d("myTag", byte.toString() )
        }

        // Send off the encoded message
        val Thread1: Thread = Thread()
        {

            val buffer = ByteArray(4)

            //Assign Ip Address 192.168.1.101
            buffer.set(0, 192.toByte())
            buffer.set(1, 168.toByte())
            buffer.set(2, 1.toByte())
            buffer.set(3, 101.toByte())
            Log.d("myTag", "the url host is: ")

            val ipAddress = InetAddress.getByAddress(buffer)

            Log.d("myTag", "the ip address is: " + ipAddress)

            val clientSocket: Socket = Socket(ipAddress, 2080)

            clientSocket.outputStream.write(encodedMessage)
            clientSocket.close()
        }
        Thread1.start()
    }

    fun readEncodingTable( jsonString: String )
    {
        // Convert the passed string to a JSONObject to access as the encoding table
        encodingTable = JSONObject( jsonString )
    }

}


