package com.codycao.jukebox

object Rating {

    public fun setRating(songIndex: Int, rating: Int){
        when(songIndex){
            R.id.radio_song1 -> {
                songOneRatingCount++
                songOneRating += (songOneRating + rating) / songOneRatingCount
            }
            R.id.radio_song2 -> {
                songTwoRating++
                songTwoRating += (songTwoRating + rating) / songTwoRatingCount
            }
            R.id.radio_song3 -> {
                songThreeRating++
                songThreeRating += (songThreeRating + rating) / songThreeRatingCount
            }
        }
    }

    public var songOneRating: Float = 0.0f
        private set
    public var songOneRatingCount: Int = 0

    public var songTwoRating: Float = 0.0f
        private set
    public var songTwoRatingCount: Int = 0

    public var songThreeRating: Float = 0.0f
        private set
    public var songThreeRatingCount: Int = 0
}