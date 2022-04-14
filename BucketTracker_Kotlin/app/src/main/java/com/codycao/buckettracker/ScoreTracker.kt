package com.codycao.buckettracker

class ScoreTracker {
    var homeScore: Int = 0
        get() = field
        set(value) {field = value}

    var guestScore: Int = 0
        get() = field
        set(value) {field = value}

    public constructor(){}
}