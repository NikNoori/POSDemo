package com.androidlearner.posdemo.data

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

//TODO: use coroutines instead of asynctasks
class MessageSender(listener: OnMessageReceived) : AsyncTask<String ,Void, Void>(){

    var mMessageListener: OnMessageReceived? = null


    override fun doInBackground(vararg params: String?): Void? {
        var message: String? = params.get(0)
      //  message = formatString(message!!)

        try {
            val socket =  Socket("192.168.68.135", 9100)
            val printWriter = PrintWriter(socket.getOutputStream())
            val bufferIn = BufferedReader(InputStreamReader(socket.getInputStream()))

            printWriter.apply{
                write(message)
                flush()
                close()
            }

            var mServerMessage = bufferIn.readLine()
            if (mServerMessage != null && mMessageListener != null) {
                //call the method messageReceived from MyActivity class
                mMessageListener!!.messageReceived(mServerMessage)
            }

          //  socket.close()


        }catch(e: IOException){
            Log.e("Message Sender", "$e ${e.message} ")
        }
        return null

    }
//
//    override fun doInBackground(vararg params: String?): TCPClient? {
//        val tcpClient = TCPClient(object: TCPClient.OnMessageReceived{
//            override fun messageReceived(message: String?) {
//                publishProgress(message)
//            }
//        })
//
//        tcpClient.run()
//
//        return null
//    }
//
//    override fun onProgressUpdate(vararg values: String?) {
//        super.onProgressUpdate(*values)
//
//        Log.d("MessageSender: Response","${values.get(0)}")
//    }

    init {
        mMessageListener = listener
    }

    interface OnMessageReceived {
        fun messageReceived(message: String?)
    }
}


