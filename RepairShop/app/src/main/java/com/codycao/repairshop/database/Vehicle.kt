package com.codycao.repairshop.database

class Vehicle {

    constructor(year: Int, makeAndModel: String, purchasePrice: Double, isNew: Boolean){
        this.year = year
        this.makeAndModel = makeAndModel
        this.purchasePrice = purchasePrice
        this.isNew = isNew
    }

    constructor(vId: Int, year: Int, makeAndModel: String, purchasePrice: Double, isNew: Boolean) :
    this(year, makeAndModel, purchasePrice, isNew) {
        this.vId = vId
    }

    override fun toString(): String {
        return "$year $makeAndModel"
    }

    var vId : Int = 0
        private set
    var year: Int
        private set
    lateinit var makeAndModel: String
        private set
    var purchasePrice: Double
        private set
    var isNew : Boolean
        private set
}