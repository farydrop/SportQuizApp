package com.example.sportquizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportquizapp.databinding.ActivityNoNetworkBinding

class NoNetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoNetworkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tryAgainButton.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
            finish()
        }
    }
}