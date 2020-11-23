package com.fr1x3.smallgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fr1x3.smallgame.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*

class MainActivity : AppCompatActivity() {

    var score : Int = 0

    lateinit var binding : ActivityMainBinding

    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal  var initialCountDown : Long = 60000
    internal var countDownInterval : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView(this,R.layout.activity_main)

        binding.handlerss = this

        //set test device. this help test real ads in debug mode
       // val testDeviceId = Arrays.asList("")
       // val configuration = RequestConfiguration.Builder()
         //                       .setTestDeviceIds(testDeviceId)
          //                      .build()

        // initialize adview
        MobileAds.initialize(this){}

        val adRequest = AdRequest.Builder().build()

        binding.adView.loadAd(adRequest)

        resetGame()
    }

    // game starting conditions
    private fun resetGame(){
        resetScore()
        binding.score = 0

        val initialTimeLeft = initialCountDown / 1000
        binding.timeLeft = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                binding.timeLeft = timeLeft
            }

            override fun onFinish() {
                endGame()
            }
        }

        gameStarted = false
    }

    //start game
    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }

    // end ame
    private fun endGame(){
        Toast.makeText(this, "Your game has ended! Score " + score.toString(), Toast.LENGTH_LONG).show()
        resetGame()
    }

    // increments counter on click

    fun incrementScore(){
        if( !gameStarted){ startGame()}
        score++
        binding.score = score
        Log.d("mainactivity" , score.toString() )
    }
    // reset score
    private fun resetScore() { score  = 0}


}