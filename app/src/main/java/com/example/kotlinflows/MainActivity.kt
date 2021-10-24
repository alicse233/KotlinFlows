package com.example.kotlinflows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        These are similar to live data on steroids(enhanced, exaggerated)
//        they also return a kind of jobs that are returned in coroutines
//        they work on concept of producer and consumer
//        they emit data streams which are collected by consumers
//        they also handle back pressure which is the scenario in which
//        a producer is emitting more data that a consumer can handle with in a time frame


//        so this one is producer
//        for every flow you need to specify which type of data it will emmit
//        here in this case it will emmit strings like flow<String>
        val flow = flow<String> {
            for (i in 0..10) {
//                emit("Hello world!")
                emit(i.toString())
//                as this one is coroutine scoped space
//                we can call delay func shown by:
                delay(1000L)
            }
        }


//        Now lets create a collector of this stream (consumer)
//        emitted from top flow (producer)
//        as flow is a suspend func we need a coroutine scope to handle it

        GlobalScope.launch {

//            we have a handy method which can collect the stream from a flow
//            which is collect() function.
//            for handling back pressure we can make buffer to handle data
//            But stay alert in defining this buffer as it takes a value which is
//            size of buffer which can get consumed soon if producer is sending a lot of streams
//            and get over flowed so stay careful with that.

            flow.buffer().collect {
                print(it)
                delay(2000L)
            }

//            you can also add operators like filter and map with it like below
//            and transform whatever comes in our  consumer flow
            flow.buffer().filter {
                it.contains("3")
            }.map { it + "Kk" }
                .collect {
                print(it)
                delay(2000L)
            }

        }
    }
}