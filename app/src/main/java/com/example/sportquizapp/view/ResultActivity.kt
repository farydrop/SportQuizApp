package com.example.sportquizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sportquizapp.R
import com.example.sportquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val correctAnsNo=intent.getStringExtra("correct")
        val totalAnsNo=intent.getStringExtra("total")
        binding.tvCorrectAns.text=correctAnsNo
        binding.tvTotalAns.text=totalAnsNo

        val percentage= (correctAnsNo?.toFloat()?.div(totalAnsNo?.toFloat()!!))?.times(100)

        if (percentage != null) {
            when {
                50 <= percentage && percentage <= 99 -> {

                    binding.tvPerformance.text="GOOD"
                    binding.llOutput.background=resources.getDrawable(R.drawable.button_background)


                }
                percentage>=100 -> {
                    binding.tvPerformance.text="EXCELLENT"
                    binding.llOutput.background=resources.getDrawable(R.drawable.right_background)
                }
                percentage<50 -> {
                    binding.tvPerformance.text="POOR"
                    binding.llOutput.background=resources.getDrawable(R.drawable.wrong_background)
                }
            }


        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,StartActivity::class.java))
    }
}