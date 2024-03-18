package com.rabbyte.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rabbyte.quizapp.R
import com.rabbyte.quizapp.adapter.LeaderAdapter
import com.rabbyte.quizapp.databinding.ActivityLeaderBinding
import com.rabbyte.quizapp.domain.UserModel

class LeaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderBinding
    private val leaderAdapter by lazy { LeaderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val window: Window = this@LeaderActivity.window
        window.statusBarColor = ContextCompat.getColor(this@LeaderActivity, R.color.grey)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            scoreTop1Txt.text = loadData()[0].score.toString()
            scoreTop2Txt.text = loadData()[1].score.toString()
            scoreTop3Txt.text = loadData()[2].score.toString()
            titleTop1Txt.text = loadData()[0].name
            titleTop2Txt.text = loadData()[1].name
            titleTop3Txt.text = loadData()[2].name

            val drawableResourceId1: Int = binding.root.resources.getIdentifier(
                loadData()[0].pic,
                "drawable",
                root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourceId1)
                .into(pic1)

            val drawableResourceId2: Int = binding.root.resources.getIdentifier(
                loadData()[1].pic,
                "drawable",
                root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourceId2)
                .into(pic2)

            val drawableResourceId3: Int = binding.root.resources.getIdentifier(
                loadData()[2].pic,
                "drawable",
                root.context.packageName
            )

            Glide.with(root.context)
                .load(drawableResourceId3)
                .into(pic3)

            bottomMenu.setItemSelected(R.id.Board)
            bottomMenu.setOnItemSelectedListener {
                if (it == R.id.home)
                    startActivity(Intent(this@LeaderActivity, MainActivity::class.java))
            }

            val list: MutableList<UserModel> = loadData()
            list.removeAt(0)
            list.removeAt(1)
            list.removeAt(2)

            leaderAdapter.differ.submitList(list)

            leaderView.apply {
                layoutManager = LinearLayoutManager(this@LeaderActivity)
                adapter = leaderAdapter
            }
        }

    }

    private fun loadData(): MutableList<UserModel> {
        val users: MutableList<UserModel> = mutableListOf()
        users.add(UserModel(1, "Sophia", "person1", 4850))
        users.add(UserModel(2, "Daniel", "person2", 4560))
        users.add(UserModel(3, "James", "person3", 3873))
        users.add(UserModel(4, "John Smith", "person4", 3250))
        users.add(UserModel(5, "Emily Johnson", "person5", 3015))
        users.add(UserModel(6, "David Brown", "person6", 2970))
        users.add(UserModel(7, "Sarah Wilson", "person7", 2870))
        users.add(UserModel(8, "Michael Davis", "person8", 2670))
        users.add(UserModel(9, "Sarah Wilson", "person9", 2380))
        users.add(UserModel(10, "Sarah Wilson", "person9", 2380))
        return users
    }
}