package com.example.kotlinretrofit.model

data class QuoteData(
    val id:String,
    val quote:String,
    val author:String,
    val thumbnail:String,
    val created_at:String,
    val updated_at:String,
)
