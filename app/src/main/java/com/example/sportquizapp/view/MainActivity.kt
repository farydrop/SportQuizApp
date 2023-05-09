package com.example.sportquizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.sportquizapp.R
import com.example.sportquizapp.databinding.ActivityMainBinding
import com.example.sportquizapp.model.QuestionModel
import com.example.sportquizapp.viewmodel.MainViewModel
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var questionsList: ArrayList<QuestionModel>
    private var index:Int = 0
    lateinit var questionModel: QuestionModel
    private var correctAnswerCount:Int=0
    private var wrongAnswerCount:Int=0
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionsList= ArrayList()
        questionsList.add(QuestionModel("What is actually electricity?","A flow of water","A flow of air","A flow of electrons"," A flow of atoms","A flow of electrons"))
        questionsList.add(QuestionModel("What is the speed of sound?","120 km/h","1,200 km/h","400 km/h","700 km/h","1,200 km/h"))
        questionsList.add(QuestionModel("What is the main component of the sun?","Liquid lava","Gas","Molten iron","Rock","Gas"))
        questionsList.add(QuestionModel("Which of the following animals can run the fastest?","Cheetah","Leopard","Tiger","Lion","Cheetah"))
        questionsList.add(QuestionModel("Which company is known for publishing the Mario video game?","Xbox","Nintendo","SEGA","Electronic Arts","Nintendo"))

        //questionsList.shuffle()
        questionModel= questionsList[index]

        setAllQuestions()

        setTimer()


    }

    private fun setTimer() {
        var duration: Long = TimeUnit.SECONDS.toMillis(15)


        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var sDuration: String = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )

                binding.tvTimer.text = sDuration

            }

            override fun onFinish() {
                index++
                if (index < questionsList.size) {
                    questionModel = questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    setTimer()

                } else {

                    gameResult()

                }
            }
        }.start()
    }


    private fun gameResult() {
        var intent= Intent(this, ResultActivity::class.java)

        intent.putExtra("correct",correctAnswerCount.toString())
        intent.putExtra("total",questionsList.size.toString())

        startActivity(intent)
    }

    private fun enableButton() {
        binding.btnOption1.isClickable=true
        binding.btnOption2.isClickable=true
        binding.btnOption3.isClickable=true
        binding.btnOption4.isClickable=true
    }

    private fun resetBackground() {
        binding.btnOption1.background=resources.getDrawable(R.drawable.button_background)
        binding.btnOption2.background=resources.getDrawable(R.drawable.button_background)
        binding.btnOption3.background=resources.getDrawable(R.drawable.button_background)
        binding.btnOption4.background=resources.getDrawable(R.drawable.button_background)
    }
    fun option1Clicked(view: View){
        disableButton()
        if(questionModel.option1==questionModel.answer){
            binding.btnOption1.background=resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption1)

        }
        else{
            wrongAns(binding.btnOption1)
        }
    }

    private fun wrongAns(option: Button) {
        option.background=resources.getDrawable(R.drawable.wrong_background)

        wrongAnswerCount++
    }

    private fun correctAns(option: Button) {
        option.background=getDrawable(R.drawable.right_background)
        correctAnswerCount++
    }

    private fun disableButton() {
        binding.btnOption1.isClickable=false
        binding.btnOption2.isClickable=false
        binding.btnOption3.isClickable=false
        binding.btnOption4.isClickable=false
    }

    fun option2Clicked(view:View){
        disableButton()
        if(questionModel.option2==questionModel.answer){
            binding.btnOption2.background=resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption2)

        }
        else{
            wrongAns(binding.btnOption2)
        }
    }
    fun option3Clicked(view:View){
        disableButton()
        if(questionModel.option3==questionModel.answer){

            binding.btnOption3.background=resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption3)


        }
        else{
            wrongAns(binding.btnOption3)
        }
    }
    fun option4Clicked(view:View){
        disableButton()
        if(questionModel.option4==questionModel.answer){
            binding.btnOption4.background=resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption4)

        }
        else{
            wrongAns(binding.btnOption4)
        }
    }

    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        }

        else {
            backToast = Toast.makeText(baseContext, "DOUBLE PRESS TO QUIT GAME", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()

    }

    private fun setAllQuestions() {
        binding.tvQuestions.text=questionModel.question
        binding.btnOption1.text=questionModel.option1
        binding.btnOption2.text=questionModel.option2
        binding.btnOption3.text=questionModel.option3
        binding.btnOption4.text=questionModel.option4
    }
}