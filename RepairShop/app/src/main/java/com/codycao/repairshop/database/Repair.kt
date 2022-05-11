package com.codycao.repairshop.database

class Repair {

    constructor(vId: Int, repairDate: String, repairCost : Double, repairDescription: String) {
        this.vId = vId
        this.repairDate = repairDate
        this.repairCost = repairCost
        this.repairDescription = repairDescription
    }

    constructor(rId: Int, vId: Int, repairDate: String, repairCost : Double, repairDescription: String)
    : this(vId, repairDate, repairCost, repairDescription) {
        this.rId = rId
    }

    var rId : Int = 0
        private set
    var vId : Int
        private set
    var repairDate : String
        private set
    var repairCost : Double
        private set
    var repairDescription : String
        private set
}