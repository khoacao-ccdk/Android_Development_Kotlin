package com.codycao.restaurant_rater_kotlin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.codycao.restaurant_rater_kotlin.databinding.ActivityViewReviewsBinding

class ViewReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewReviewsBinding
    private lateinit var reviews: Reviews

    private val button_add_review_activity = View.OnClickListener {
        startForResult.launch(Intent(ViewReviewsActivity@this, AddReviewActivity::class.java))
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK && result.data != null){
            val restaurantName = result.data!!.getStringExtra(AddReviewActivity.EXTRA_RESTAURANT_NAME)!!
            val reviewDate = result.data!!.getStringExtra(AddReviewActivity.EXTRA_REVIEW_DATE)!!
            val reviewTime = result.data!!.getStringExtra(AddReviewActivity.EXTRA_REVIEW_TIME)!!
            val reviewMeal = result.data!!.getStringExtra(AddReviewActivity.EXTRA_REVIEW_MEAL)!!
            val rating = result.data!!.getIntExtra(AddReviewActivity.EXTRA_RATING, 0)
            val isFavorite = result.data!!.getBooleanExtra(AddReviewActivity.EXTRA_IS_FAVORITE, false)
            val reviewToAdd = Review(restaurantName, reviewDate, reviewTime, reviewMeal, rating, isFavorite)
            reviews.addReview(reviewToAdd)
            binding.listviewReviews.adapter = reviews
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonAddReview.setOnClickListener(button_add_review_activity)

        reviews = Reviews(this)
        reviews.getReviews()
        binding.listviewReviews.adapter = reviews
    }
}