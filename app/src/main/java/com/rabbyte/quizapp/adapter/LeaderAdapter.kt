package com.rabbyte.quizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabbyte.quizapp.databinding.ViewholderLeaderBinding
import com.rabbyte.quizapp.domain.UserModel

class LeaderAdapter : RecyclerView.Adapter<LeaderAdapter.ViewHolder>() {

    private lateinit var binding: ViewholderLeaderBinding

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderLeaderBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ViewholderLeaderBinding.bind(holder.itemView)
        binding.titleTxt.text = differ.currentList[position].name

        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            differ.currentList[position].pic,
            "drawable",
            binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.pic)

        binding.rowTxt.text = buildString {
            append(position + 4).toString()
        }
        binding.scoreTxt.text = differ.currentList[position].score.toString()
    }

    private val differCallBack = object : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}