package com.codycao.repairshop.database

class VehicleRepair {

    constructor(v : Vehicle, r : Repair){
        this.v = v
        this.r = r
    }

    var v : Vehicle
        private set
    var r : Repair
        private set
}