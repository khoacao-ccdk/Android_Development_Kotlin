package com.codycao.restaurant_rater_kotlin

class Review {

    constructor(
        restaurantName: String,
        reviewDate: String,
        reviewTime: String,
        reviewMeal: String,
        restaurantRating: Int,
        isFavorite: Boolean,
    ) {
        this.restaurantName = restaurantName
        this.reviewDate = reviewDate
        this.reviewTime = reviewTime
        this.reviewMeal = reviewMeal
        this.restaurantRating = restaurantRating
        this.isFavorite = isFavorite
    }

    override fun toString(): String {
        return "Review(restaurantName='$restaurantName', reviewDate='$reviewDate', reviewTime='$reviewTime', reviewMeal='$reviewMeal', restaurantRating=$restaurantRating, isFavorite=$isFavorite)"
    }

    var restaurantName: String
        private set
    var reviewDate: String
        private set
    var reviewTime: String
        private set
    var reviewMeal: String
        private set
    var restaurantRating: Int = 0
        private set
    var isFavorite: Boolean = false
        private set
}