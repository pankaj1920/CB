package com.example.carryboxkotlin.corotine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carryboxkotlin.R
import com.example.kotlinretrofit.QuoteAdapter
import com.example.kotlinretrofit.api.BaseClient
import com.example.kotlinretrofit.model.QuoteData
import com.example.kotlinretrofit.model.QuoteModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CorotinesActivity : AppCompatActivity() {

    lateinit var quoteRecycler: RecyclerView
    lateinit var adapter: QuoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corotines)

        quoteRecycler = findViewById(R.id.quoteRecycler)

        quoteRecycler.layoutManager = LinearLayoutManager(this)

        //now we have corotine scope and we can use it to launch our suspending function


   /*     CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.Main) {
                getQuote()
            }
        }*/

        CoroutineScope(Dispatchers.Main).launch {

            val quote = getQuote()
            if (quote!!.isSuccessful.equals("true")) {
                Toast.makeText(applicationContext, "Message "+quote.isSuccessful, Toast.LENGTH_SHORT).show()
                val quoteData: List<QuoteData> = quote.quotes
                quoteRecycler.adapter = QuoteAdapter(quoteData)
            } else {
                Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
            }
        }


    }


    private suspend fun getQuote() :QuoteModel{
//        val response = BaseClient.instance.getQuote()
        // here we want to use io dispatcher of this network call and to make it in the io dispatcher
        // we can use the function withContext to run in io thread and
        // it will return it to our main corotine scope that is using the main dispatcher
        return withContext(Dispatchers.IO){
            BaseClient.instance.getQuote().body()!!

        }
/*
        if (response.isSuccessful) {
            val quoteModel: QuoteModel? = response.body()
            if (quoteModel!!.isSuccessful.equals("true")) {
                val quoteData: List<QuoteData> = quoteModel.quotes
                quoteRecycler.adapter = QuoteAdapter(quoteData)
            } else {
                Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        }*/
    }


/*

    private suspend fun getQuote() {
        val response = BaseClient.instance.getQuote()

        if (response.isSuccessful) {
            val quoteModel: QuoteModel? = response.body()
            if (quoteModel!!.isSuccessful.equals("true")) {
                val quoteData: List<QuoteData> = quoteModel.quotes
                quoteRecycler.adapter = QuoteAdapter(quoteData)
            } else {
                Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        }
    }

*/



    /* private fun getQuote() {
         val call:Call<QuoteModel> = BaseClient.instance.getQuote()

         call.enqueue(object : Callback<QuoteModel> {
             override fun onResponse(call: Call<QuoteModel>, response: Response<QuoteModel>) {

                 if (response.isSuccessful) {
                     val quoteModel: QuoteModel? = response.body()
                     if (quoteModel!!.isSuccessful.equals("true")) {
                         val quoteData:List<QuoteData> = quoteModel.quotes
                         quoteRecycler.adapter = QuoteAdapter(quoteData)
                     } else {
                         Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
                     }
                 } else {
                     Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                         .show()
                 }
             }

             override fun onFailure(call: Call<QuoteModel>, t: Throwable) {
                 Toast.makeText(applicationContext, "On Failure " + t.message, Toast.LENGTH_SHORT)
                     .show()
             }

         })
     }*/

}