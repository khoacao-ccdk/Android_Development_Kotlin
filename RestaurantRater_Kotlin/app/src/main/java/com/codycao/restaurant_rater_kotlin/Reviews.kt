package com.codycao.restaurant_rater_kotlin

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class Reviews() : BaseAdapter() {

    constructor(context: Context) : this() {
        this.context = context
    }

    override fun getCount(): Int {
        return reviews.size
    }

    override fun getItem(p0: Int): Any {
        return reviews[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listview_review_row, parent, false)
        val ratingBar_is_favorite = view.findViewById<RatingBar>(R.id.ratingBar_is_favorite)
        val editText_restaurant_name = view.findViewById<TextView>(R.id.textView_restaurant_name)
        val seekBar_rating = view.findViewById<ProgressBar>(R.id.progressBar_rating)
        val radioGroup_rating_display = view.findViewById<RadioGroup>(R.id.radioGroup_rating_display)
        val review = reviews[postion]
        ratingBar_is_favorite.progress = if(review.isFavorite) 1 else 0
        editText_restaurant_name.setText(review.restaurantName)
        seekBar_rating.progress = review.restaurantRating
        when(review.reviewMeal){
            "Breakfast" -> radioGroup_rating_display.check(R.id.radio_breakfast_display)
            "Lunch" -> radioGroup_rating_display.check(R.id.radio_lunch_display)
            else -> radioGroup_rating_display.check(R.id.radio_dinner_display)
        }

        view.setOnClickListener {
            val alert = AlertDialog.Builder(context)
                .setMessage(String.format("This review was created on %s at %s",
                    review.reviewDate,
                    review.reviewTime))
                .create()
            alert.show()
        }

        return view
    }

    fun getReviews(){
        val myFile = File(context.getFilesDir(), "reviews.csv")
        Scanner(myFile)
            .use{
                while(it.hasNextLine()){
                    var line = it.nextLine().split(",")
                    val restaurantName = line[0]
                    val reviewDate = line[1]
                    val reviewTime = line[2]
                    val reviewMeal = line[3]
                    val rating = Integer.valueOf(line[4])
                    val isFavorite = (line[5] == "1")
                    val review = Review(restaurantName, reviewDate, reviewTime, reviewMeal, rating, isFavorite)
                    reviews.add(review)
                }
                it.close()
            }
    }

    fun addReview(reviewToAdd: Review){
        reviews.add(reviewToAdd)
    }

    private var reviews = ArrayList<Review>()
    private lateinit var context: Context
}