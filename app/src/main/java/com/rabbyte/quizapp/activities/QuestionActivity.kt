package com.rabbyte.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rabbyte.quizapp.R
import com.rabbyte.quizapp.adapter.QuestionAdapter
import com.rabbyte.quizapp.databinding.ActivityQuestionBinding
import com.rabbyte.quizapp.domain.QuestionModel

class QuestionActivity : AppCompatActivity(), QuestionAdapter.Score {

    private lateinit var binding: ActivityQuestionBinding
    private var position: Int = 0
    private var receivedList: MutableList<QuestionModel> = mutableListOf()
    private var allScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val window: Window = this@QuestionActivity.window
        window.statusBarColor = ContextCompat.getColor(this@QuestionActivity, R.color.grey)

        receivedList = intent.getParcelableArrayListExtra<QuestionModel>("list")!!.toMutableList()

        binding.apply {
            backBtn.setOnClickListener { finish() }

                progressBar.progress = 1

                questionTxt.text = receivedList[position].question
                val drawableResourceId = root.resources.getIdentifier(
                    receivedList[position].picPath,
                    "drawable",
                    packageName
                )

                Glide.with(this@QuestionActivity)
                    .load(drawableResourceId)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)

                loadAnswers()

                rightArrow.setOnClickListener {
                    if (progressBar.progress == 10) {
                        val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
                        intent.putExtra("score", allScore)
                        startActivity(intent)
                        finish()
                        return@setOnClickListener
                    }
                    position++
                    progressBar.progress++
                    questionNumberTxt.text = buildString {
                        append("Question ")
                        append(progressBar.progress)
                        append("/10")
                    }
                    questionTxt.text = receivedList[position].question

                    val drawableResourceId = resources.getIdentifier(
                        receivedList[position].picPath,
                        "drawable",
                        packageName
                    )

                    Glide.with(this@QuestionActivity)
                        .load(drawableResourceId)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                        .into(pic)

                    loadAnswers()

                }

                leftArrow.setOnClickListener {
                    if (progressBar.progress == 1) {
                        return@setOnClickListener
                    }

                    position--

                    progressBar.progress--
                    questionNumberTxt.text = buildString {
                        append("Question ")
                        append(progressBar.progress)
                        append("/10")
                    }
                    questionTxt.text = receivedList[position].question

                    val drawableResourceId = resources.getIdentifier(
                        receivedList[position].picPath,
                        "drawable",
                        packageName
                    )

                    Glide.with(this@QuestionActivity)
                        .load(drawableResourceId)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                        .into(pic)

                    loadAnswers()
                }
            }
        }

    private fun loadAnswers() {
        val users: MutableList<String> = mutableListOf()
        users.add(receivedList[position].ans1.toString())
        users.add(receivedList[position].ans2.toString())
        users.add(receivedList[position].ans3.toString())
        users.add(receivedList[position].ans4.toString())

        if (receivedList[position].clickedAns != null) users.add(receivedList[position].clickedAns.toString())

        val questionAdapter by lazy {
            QuestionAdapter(
                receivedList[position].correctAns.toString(), users, this
            )
        }

        questionAdapter.differ.submitList(users)
        binding.questionList.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            adapter = questionAdapter
        }


    }

    override fun amount(number: Int, clickedAns: String) {
        allScore += number
        receivedList[position].clickedAns = clickedAns
    }
}