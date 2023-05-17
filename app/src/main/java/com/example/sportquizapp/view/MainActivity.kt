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
        questionsList.add(QuestionModel("What's the sanctioning body for Formula One?","TIFA","UN","FIA","USA","FIA"))
        questionsList.add(QuestionModel("What does the \"formula\" mean in Formula One racing?","it's a formula for success","it's a set of regulations","it's the formula of the fuel","it's a method for changing tires","it's a set of regulations"))
        questionsList.add(QuestionModel("What special thing do race organizers do with manhole covers for the Monaco Grand Prix?","take them off","weld them down","paint them hot pink","replace them with unicorns","weld them down"))
        questionsList.add(QuestionModel("In what year was the first Formula One race held?","1950","1937","1955","1963","1950"))
        questionsList.add(QuestionModel("What does FIA require for F1 cars' undertrays?","it curves at 10 degrees","it's completely flat between the two axles","it is wavy on the surface","it has a corrugated surface for resistance","it's completely flat between the two axles"))

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