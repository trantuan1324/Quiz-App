package com.rabbyte.quizapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    val id: Int,
    val question: String?,
    val ans1: String?,
    val ans2: String?,
    val ans3: String?,
    val ans4: String?,
    val correctAns: String?,
    val score: Int,
    val picPath: String?,
    var clickedAns: String?
) : Parcelable