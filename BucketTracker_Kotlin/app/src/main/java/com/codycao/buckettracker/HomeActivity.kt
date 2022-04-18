package com.codycao.buckettracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.codycao.buckettracker.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val DEFAULT_NUM_MINS: Int = 20
    private val MILLIS_PER_MIN: Long = 60000
    private val MILLIS_PER_SEC: Long = 1000
    private val SECS_PER_MIN: Int = 60

    private var scoreTracker: ScoreTracker? = null
    private var timer: CountDownTimer? = null

    //Listeners
    private val button_add_score_longClickListener: View.OnLongClickListener = View.OnLongClickListener{
        var score: Int = 0
        if(binding.checkboxAddOne.isChecked) {
            score += 1
            binding.checkboxAddOne.isChecked = false
        }
        if(binding.checkboxAddTwo.isChecked) {
            score += 2
            binding.checkboxAddTwo.isChecked = false
        }
        if(binding.checkboxAddThree.isChecked) {
            score += 3
            binding.checkboxAddThree.isChecked = false
        }

        if(binding.toggleIsGuest.isChecked) {
            scoreTracker!!.guestScore += score
            binding.textviewGuestScore.text = scoreTracker!!.guestScore.toString()
        }
        else {
            scoreTracker!!.homeScore += score
            binding.textviewHomeScore.text = scoreTracker!!.homeScore.toString()
        }

        binding.toggleIsGuest.toggle()
        setBallPossession()
        true
    }

    private val toggle_is_guest_clickListener: View.OnClickListener = View.OnClickListener {
        setBallPossession()
    }

    private val switch_game_clock_clickListener: View.OnClickListener = View.OnClickListener {
        if(binding.switchGameClock.isChecked){
            val timeString = binding.textviewTimeRemaining.text.toString().split(":")
            var min: Int = timeString[0].toInt()
            var sec = timeString[1].toInt()
            var timeRemaining: Long = min * MILLIS_PER_MIN + sec * MILLIS_PER_SEC

            timer = object : CountDownTimer(timeRemaining, MILLIS_PER_SEC){
                override fun onTick(millisUntilFinished: Long){
                    if(sec == 0){
                        min--
                        sec = 59
                    }
                    else{
                        sec --
                    }
                    setTime(min, sec)
                }

                override fun onFinish(){
                    binding.switchGameClock.toggle()
                }
            }.start()
        }
        else{
            timer!!.cancel()
            timer = null
        }
    }

    private val button_set_time_clickListener: View.OnClickListener = View.OnClickListener {
        if(binding.edittextNumMins.text.toString() != "" && binding.edittextNumSecs.text.toString() != "") {
            val min: Int = binding.edittextNumMins.text.toString().toInt()
            val sec: Int = binding.edittextNumSecs.text.toString().toInt()
            if (min < 20 && sec < 60) {
                if (timer != null) timer!!.cancel()
                timer = null
                binding.switchGameClock.isChecked = false
                setTime(min, sec)
            }
        }
    }

    //Helpers
    private fun setBallPossession(){
        val black: Int = resources.getColor(R.color.black)
        val red: Int = resources.getColor(R.color.red)
        var homeColor: Int
        var guestColor: Int
        if(!binding.toggleIsGuest.isChecked){
            homeColor = red
            guestColor = black
        }
        else{
            homeColor = black
            guestColor = red
        }

        binding.labelHome.setTextColor(homeColor)
        binding.textviewHomeScore.setTextColor(homeColor)
        binding.labelGuest.setTextColor(guestColor)
        binding.textviewGuestScore.setTextColor(guestColor)
    }

    private fun setTime(min: Int, sec: Int){
        val minString: String = if(min >= 10) min.toString() else "0$min"
        val secString: String = if(sec >= 10) sec.toString() else "0$sec"
        val newTime: String = "$minString:$secString"
        binding.textviewTimeRemaining.text = newTime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBallPossession()
        binding.toggleIsGuest.setOnClickListener(toggle_is_guest_clickListener)
        binding.buttonAddScore.setOnLongClickListener(button_add_score_longClickListener)
        binding.switchGameClock.setOnClickListener(switch_game_clock_clickListener)
        binding.buttonSetTime.setOnClickListener(button_set_time_clickListener)
        scoreTracker = ScoreTracker()
    }
}