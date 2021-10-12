package com.androidlearner.posdemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.androidlearner.posdemo.R
import com.androidlearner.posdemo.data.MessageSender
import com.androidlearner.posdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setListener()

    }

    private fun setListener() {
        binding.transferBtn.setOnClickListener {
            message = binding.editText.text.trim().toString() + "$\n"
            if (!message.isNullOrEmpty()) {
                transferData(message)
            } else {
                Toast.makeText(this, "Type something in the box.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun transferData(message: String?) {
        if (message != null) {
            val messageSender = MessageSender(object : MessageSender.OnMessageReceived {
                override fun messageReceived(message: String?) {
                    updateUI(message)
                }
            })
            messageSender.execute(message)
            binding.editText.text.clear()
        }

    }

    private fun updateUI(message: String?) {
        binding.responseTv.text = message
    }


}