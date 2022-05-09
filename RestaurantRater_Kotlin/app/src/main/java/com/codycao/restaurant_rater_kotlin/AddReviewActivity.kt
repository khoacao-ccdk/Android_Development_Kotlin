package com.codycao.restaurant_rater_kotlin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.codycao.restaurant_rater_kotlin.databinding.ActivityAddReviewBinding
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime

class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewBinding

    private val button_add_review_clickListener = View.OnClickListener {
        val restaurantName = binding.edittextRestaurantName.text.toString()
        val reviewDate = binding.edittextReviewDate.text.toString()
        val reviewTime = binding.edittextReviewTime.text.toString()
        val reviewMeal = when(binding.radiogroupMeals.checkedRadioButtonId){
            R.id.radio_breakfast -> "Breakfast"
            R.id.radio_lunch -> "Lunch"
            R.id.radio_dinner -> "Dinner"
            else -> {""}
        }
        val rating = binding.seekbarRating.progress
        val isFavorite = binding.checkboxFavorite.isChecked
        if(restaurantName == "" || reviewDate == "" || reviewTime == "") {
            val toast = Toast.makeText(AddReviewActivity@this, "Please fill in all information", Toast.LENGTH_SHORT)
            toast.show()
        }else {
            var returnIntent = Intent()
            returnIntent.putExtra(EXTRA_RESTAURANT_NAME, restaurantName)
            returnIntent.putExtra(EXTRA_REVIEW_DATE, reviewDate)
            returnIntent.putExtra(EXTRA_REVIEW_TIME, reviewTime)
            returnIntent.putExtra(EXTRA_REVIEW_MEAL, reviewMeal)
            returnIntent.putExtra(EXTRA_RATING, rating)
            returnIntent.putExtra(EXTRA_IS_FAVORITE, isFavorite)

            val myFile = File(filesDir,"reviews.csv")
            FileWriter(myFile, true)
            .use{
                val newRecord = String.format("%s,%s,%s,%s,%s,%s\n",
                                restaurantName,
                                reviewDate,
                                reviewTime,
                                reviewMeal,
                                rating,
                                if(isFavorite) "1" else "0")
                it.write(newRecord)
                it.close()
            }

            val toast = Toast.makeText(AddReviewActivity@this, "Review Added Successfully", Toast.LENGTH_SHORT)
            toast.show()

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private val edittext_review_date_clickListener = View.OnClickListener {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dateOfMonth ->
            run {
                binding.edittextReviewDate.setText(String.format("%s/%s/%s", month+1, dateOfMonth, year))
            }
        }
        val today = LocalDateTime.now()
        val dialog = DatePickerDialog(AddReviewActivity@this, dateSetListener, today.year, today.monthValue-1, today.dayOfMonth)
        dialog.show()
    }

    private val edittext_review_time_clickListener = View.OnClickListener {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { picker, hour, min ->
            var hourString: String = hour.toString()
            var indicator = "AM"
            if(hour > 12){
                val hour = hour%12
                hourString = hour.toString()
                indicator = "PM"
            }
            val minString = if(min >= 10) min.toString() else "0$min"
            binding.edittextReviewTime.setText(String.format("%s:%s %s", hourString, minString, indicator))
        }
        val today = LocalDateTime.now().toLocalTime()
        val dialog = TimePickerDialog(AddReviewActivity@this, timeSetListener, today.hour, today.minute, true)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddReview.setOnClickListener(button_add_review_clickListener)
        binding.edittextReviewDate.setOnClickListener(edittext_review_date_clickListener)
        binding.edittextReviewTime.setOnClickListener(edittext_review_time_clickListener)
    }

    companion object {
        const val EXTRA_RESTAURANT_NAME = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_RESTAURENT_NAME"
        const val EXTRA_REVIEW_DATE = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_REVIEW_DATE"
        val EXTRA_REVIEW_TIME = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_REVIEW_TIME"
        val EXTRA_REVIEW_MEAL = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_REVIEW_MEAL"
        val EXTRA_RATING = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_RATING"
        val EXTRA_IS_FAVORITE = "COM.CODYCAO.RESTAURANT_RATER_KOTLIN.EXTRA_IS_FAVORITE"
    }
}