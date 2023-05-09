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
    /*lateinit var questionsList: ArrayList<QuestionModel>
    private var index:Int = 0
    lateinit var questionModel: QuestionModel
    private var correctAnswerCount:Int=0
    private var wrongAnswerCount:Int=0
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null*/
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*questionsList= ArrayList()
        questionsList.add(QuestionModel("What is actually electricity?","A flow of water","A flow of air","A flow of electrons"," A flow of atoms","A flow of electrons"))
        questionsList.add(QuestionModel("What is the speed of sound?","120 km/h","1,200 km/h","400 km/h","700 km/h","1,200 km/h"))
        questionsList.add(QuestionModel("What is the main component of the sun?","Liquid lava","Gas","Molten iron","Rock","Gas"))
        questionsList.add(QuestionModel("Which of the following animals can run the fastest?","Cheetah","Leopard","Tiger","Lion","Cheetah"))
        questionsList.add(QuestionModel("Which company is known for publishing the Mario video game?","Xbox","Nintendo","SEGA","Electronic Arts","Nintendo"))

        //questionsList.shuffle()
        questionModel= questionsList[index]

        setAllQuestions()

        setTimer()*/

        viewModel.timer.observe(this) {
            binding.tvTimer.text = it
        }

        viewModel.showGameResult.observe(this) {
            var intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("correct", it.first)
            intent.putExtra("total", it.second)

            startActivity(intent)
        }

        /*viewModel.question.observe(this){
            binding.tvQuestions.text = it
        }
        viewModel.option1.observe(this){
            binding.btnOption1.text = it
        }
        viewModel.option2.observe(this){
            binding.btnOption2.text = it
        }
        viewModel.option3.observe(this){
            binding.btnOption3.text = it
        }
        viewModel.option4.observe(this){
            binding.btnOption4.text = it
        }*/

        viewModel.onEnable.observe(this) {
            binding.btnOption1.isClickable = it
            binding.btnOption2.isClickable = it
            binding.btnOption3.isClickable = it
            binding.btnOption4.isClickable = it
        }

        viewModel.onDisable.observe(this) {
            binding.btnOption1.isClickable = it
            binding.btnOption2.isClickable = it
            binding.btnOption3.isClickable = it
            binding.btnOption4.isClickable = it
        }

        viewModel.wrongAns.observe(this){
            it.background = resources.getDrawable(R.drawable.wrong_background)
        }

        viewModel.correctAns.observe(this){
            it.background = resources.getDrawable(R.drawable.right_background)
        }

    }


    /*private fun gameResult() {
        var intent= Intent(this, ResultActivity::class.java)

        intent.putExtra("correct",correctAnswerCount.toString())
        intent.putExtra("total",questionsList.size.toString())

        startActivity(intent)
    }*/

    /*private fun enableButton() {
        binding.btnOption1.isClickable=true
        binding.btnOption2.isClickable=true
        binding.btnOption3.isClickable=true
        binding.btnOption4.isClickable=true
    }*/

    /* private fun resetBackground() {
         binding.btnOption1.background=resources.getDrawable(R.drawable.button_background)
         binding.btnOption2.background=resources.getDrawable(R.drawable.button_background)
         binding.btnOption3.background=resources.getDrawable(R.drawable.button_background)
         binding.btnOption4.background=resources.getDrawable(R.drawable.button_background)
     }*/
    fun option1Clicked(view: View) {
        disableButton()
        if (questionModel.option1 == questionModel.answer) {
            binding.btnOption1.background = resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption1)

        } else {
            wrongAns(binding.btnOption1)
        }
    }

    /*private fun wrongAns(option: Button) {
        option.background = resources.getDrawable(R.drawable.wrong_background)

        wrongAnswerCount++
    }

    private fun correctAns(option: Button) {
        option.background = getDrawable(R.drawable.right_background)
        correctAnswerCount++
    }*/

    /*private fun disableButton() {
        binding.btnOption1.isClickable=false
        binding.btnOption2.isClickable=false
        binding.btnOption3.isClickable=false
        binding.btnOption4.isClickable=false
    }*/

    /*fun option2Clicked(view: View) {
        disableButton()
        if (questionModel.option2 == questionModel.answer) {
            binding.btnOption2.background = resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption2)

        } else {
            wrongAns(binding.btnOption2)
        }
    }

    fun option3Clicked(view: View) {
        disableButton()
        if (questionModel.option3 == questionModel.answer) {

            binding.btnOption3.background = resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption3)


        } else {
            wrongAns(binding.btnOption3)
        }
    }

    fun option4Clicked(view: View) {
        disableButton()
        if (questionModel.option4 == questionModel.answer) {
            binding.btnOption4.background = resources.getDrawable(R.drawable.right_background)


            correctAns(binding.btnOption4)

        } else {
            wrongAns(binding.btnOption4)
        }
    }*/

    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        } else {
            backToast = Toast.makeText(baseContext, "DOUBLE PRESS TO QUIT GAME", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()

    }

    /*private fun setAllQuestions() {
        binding.tvQuestions.text=questionModel.question
        binding.btnOption1.text=questionModel.option1
        binding.btnOption2.text=questionModel.option2
        binding.btnOption3.text=questionModel.option3
        binding.btnOption4.text=questionModel.option4
    }*/
}