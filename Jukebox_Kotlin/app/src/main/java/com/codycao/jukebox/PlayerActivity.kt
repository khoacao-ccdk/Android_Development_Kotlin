package com.codycao.jukebox

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import com.codycao.jukebox.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(){

    private lateinit var binding: ActivityPlayerBinding

    //Listeners
    private val button_cast_vote_clickListener = View.OnClickListener {
        Rating.setRating(binding.radiogroupSongs.checkedRadioButtonId, binding.ratingbarVoterRating.progress)
        //Update the view
        when(binding.radiogroupSongs.checkedRadioButtonId){
            R.id.radio_song1 -> {
                binding.progressbarAverageRating1.progress = Math.round(Rating.songOneRating)
                binding.textviewNumVotes1.text = Rating.songOneRatingCount.toString()
            }
            R.id.radio_song2 -> {
                binding.progressbarAverageRating2.progress = Math.round(Rating.songTwoRating)
                binding.textviewNumVotes2.text = Rating.songTwoRatingCount.toString()
            }
            R.id.radio_song3 -> {
                binding.progressbarAverageRating3.progress = Math.round(Rating.songThreeRating)
                binding.textviewNumVotes3.text = Rating.songThreeRatingCount.toString()
            }
        }
        binding.ratingbarVoterRating.progress = 0
    }

    private val radiogroup_songs_checkedChangeListener = RadioGroup.OnCheckedChangeListener { _, p1 ->
        PLayer.stop()
        PLayer.play(this@PlayerActivity, p1)
        setImage(p1)
    }

    private val seekbars_seekbarChangeListener = object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            when (p0!!.id) {
                R.id.seekbar_red,
                R.id.seekbar_green,
                R.id.seekbar_blue
                -> {
                    val red = binding.seekbarRed.progress
                    val green = binding.seekbarGreen.progress
                    val blue = binding.seekbarBlue.progress

                    binding.constraintlayout.setBackgroundColor(Color.argb(255, red, green, blue))

                    binding.textviewRed.text = red.toString()
                    binding.textviewGreen.text = green.toString()
                    binding.textviewBlue.text = blue.toString()
                }
                R.id.seekbar_song_position -> {
                    PLayer.seek(binding.seekbarSongPosition.progress)
                }
            }
        }
        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCastVote.setOnClickListener(button_cast_vote_clickListener)
        binding.radiogroupSongs.setOnCheckedChangeListener(radiogroup_songs_checkedChangeListener)
        binding.seekbarBlue.setOnSeekBarChangeListener(seekbars_seekbarChangeListener)
        binding.seekbarGreen.setOnSeekBarChangeListener(seekbars_seekbarChangeListener)
        binding.seekbarRed.setOnSeekBarChangeListener(seekbars_seekbarChangeListener)
        binding.seekbarSongPosition.setOnSeekBarChangeListener(seekbars_seekbarChangeListener)

        binding.seekbarRed.progress = binding.seekbarRed.max
        binding.seekbarGreen.progress = binding.seekbarGreen.max
        binding.seekbarBlue.progress = binding.seekbarBlue.max

        PLayer.play(this, binding.radiogroupSongs.checkedRadioButtonId)
        setImage(binding.radiogroupSongs.checkedRadioButtonId)
    }

    //Helpers
    private fun setImage(radioButtonId: Int){
        binding.imageviewAlbumCover.setImageDrawable(when(radioButtonId){
            R.id.radio_song1 -> getResources().getDrawable(R.drawable.track1)
            R.id.radio_song2 -> getResources().getDrawable(R.drawable.track2)
            else -> getResources().getDrawable(R.drawable.track3)
        })
    }
}