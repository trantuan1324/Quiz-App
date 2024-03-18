package com.rabbyte.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rabbyte.quizapp.R
import com.rabbyte.quizapp.databinding.ViewholderQuestionBinding

class QuestionAdapter(
    private val correctAns: String,
    private val users: MutableList<String> = mutableListOf(),
    private var returnScore: Score
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private lateinit var binding: ViewholderQuestionBinding

    interface Score { fun amount(number: Int, clickedAns: String) }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderQuestionBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ViewholderQuestionBinding.bind(holder.itemView)
        binding.answerTxt.text = differ.currentList[position]
        var correctPos = 0
        when (correctAns) {
            "a" -> {
                correctPos = 0
            }
            "b" -> {
                correctPos = 1
            }
            "c" -> {
                correctPos = 2
            }
            "d" -> {
                correctPos = 3
            }
        }

        if (differ.currentList.size == 5 && correctPos == position) {
            binding.answerTxt.setBackgroundResource(R.drawable.green_background)
            binding.answerTxt.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )
            val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.tick)
            binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        }

        if (differ.currentList.size == 5) {
            var clickedPos = 0
            when (differ.currentList[4]) {
                "a" -> {
                    clickedPos = 0
                }
                "b" -> {
                    clickedPos = 1
                }
                "c" -> {
                    clickedPos = 2
                }
                "d" -> {
                    clickedPos = 3
                }
            }

            if (clickedPos == position && clickedPos != correctPos) {
                binding.answerTxt.setBackgroundResource(R.drawable.red_background)
                binding.answerTxt.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.thieves)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            }
        }

        if (position == 4)
            binding.root.visibility = View.GONE

        holder.itemView.setOnClickListener {
            var str = ""
            when (position) {
                0 -> {
                    str = "a"
                }
                1 -> {
                    str = "b"
                }
                2 -> {
                    str = "c"
                }
                3 -> {
                    str = "d"
                }
            }

            users.add(4, str)
            notifyDataSetChanged()

            if (correctPos == position) {
                binding.answerTxt.setBackgroundResource(R.drawable.green_background)
                binding.answerTxt.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.tick)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(5, str)
            } else {
                binding.answerTxt.setBackgroundResource(R.drawable.red_background)
                binding.answerTxt.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                val drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.thieves)
                binding.answerTxt.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                returnScore.amount(0, str)
            }
        }
        if (differ.currentList.size == 5) holder.itemView.setOnClickListener(null)
    }

    private val differCallBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)
}