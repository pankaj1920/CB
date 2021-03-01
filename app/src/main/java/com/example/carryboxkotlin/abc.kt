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

class CorotinesActivity : AppCompatActivity(),CoroutineScope {

    lateinit var quoteRecycler: RecyclerView
    lateinit var adapter: QuoteAdapter

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        // here we are using Dispatchers.Main bcz our app get crashed if we toch the view from other than main thread
        // so we are use Dispatchers.Main as a corotine context
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corotines)

        //when our activity is created we should inatalize the job and
        // in on distory we need to destory the job
        job = Job()

        quoteRecycler = findViewById(R.id.quoteRecycler)

        quoteRecycler.layoutManager = LinearLayoutManager(this)

        //now we have corotine scope and we can use it to launch our suspending function
        launch {
            //here we are making the network request in main thread
            val quote = getQuote()
            quoteRecycler.adapter = QuoteAdapter(quote)
        }

        /*  CoroutineScope(Dispatchers.IO).launch {

              withContext(Dispatchers.Main) {
                  getQuote()
              }
          }*/


    }


    private suspend fun getQuote() :List<QuoteData>{
//        val response = BaseClient.instance.getQuote()
        // here we want to use io dispatcher of this network call and to make it in the io dispatcher
        // we can use the function withContext to run in io thread and
        // it will return it to our main corotine scope that is using the main dispatcher
        return withContext(Dispatchers.IO){
            BaseClient.instance.getQuote().body()?.quotes!!

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

    override fun onDestroy() {
        super.onDestroy()
        // with the help of this cancle whenever the activity or fragment get destored all the corotines will be cancled
        job.cancel()
    }
}