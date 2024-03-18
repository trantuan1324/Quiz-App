package com.rabbyte.quizapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rabbyte.quizapp.R
import com.rabbyte.quizapp.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {

    private var score: Int = 0
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        score = intent.getIntExtra("score", 0)

        binding.apply {
            scoreTxt.text = score.toString()
            backBtn.setOnClickListener {
                startActivity(Intent(this@ScoreActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}