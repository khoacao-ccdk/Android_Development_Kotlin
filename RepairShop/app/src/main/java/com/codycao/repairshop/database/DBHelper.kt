package com.codycao.repairshop.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder

class DBHelper(private var context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Create Vehicle Table
        val createVehicleTable = StringBuilder()
            .append("CREATE TABLE $TABLE_VEHICLE (")
            .append("$COL_VEHICLE_ID INTEGER, ")
            .append("$COL_VEHICLE_YEAR INTEGER NOT NULL, ")
            .append("$COL_VEHICLE_MAKE_MODEL TEXT NOT NULL, ")
            .append("$COL_VEHICLE_PURCHASE_PRICE REAL NOT NULL, ")
            .append("$COL_VEHICLE_IS_NEW INTEGER NOT NULL, ")
            .append("PRIMARY KEY($COL_VEHICLE_ID AUTOINCREMENT))").toString()
        db!!.execSQL(createVehicleTable)

        val createRepairTable = StringBuilder()
            .append("CREATE TABLE $TABLE_REPAIR (")
            .append("$COL_REPAIR_ID INTEGER, ")
            .append("$COL_REPAIR_VEHICLE_ID INTEGER NOT NULL, ")
            .append("$COL_REPAIR_DATE TEXT NOT NULL, ")
            .append("$COL_REPAIR_COST REAL NOT NULL, ")
            .append("$COL_REPAIR_DESCRIPTION TEXT NOT NULL, ")
            .append("PRIMARY KEY($COL_REPAIR_ID AUTOINCREMENT))").toString()
        db!!.execSQL(createRepairTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addVehicle(vehicleToAdd: Vehicle) : Long{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_VEHICLE_YEAR, vehicleToAdd.year)
        cv.put(COL_VEHICLE_MAKE_MODEL, vehicleToAdd.makeAndModel)
        cv.put(COL_VEHICLE_PURCHASE_PRICE, vehicleToAdd.purchasePrice)
        cv.put(COL_VEHICLE_IS_NEW, vehicleToAdd.isNew)
        val result = db.insert(TABLE_VEHICLE, null, cv)
        db.close()
        return result
    }

    fun getAllVehicle() : List<Vehicle>{
        val db = readableDatabase
        val query = String.format("SELECT * FROM %s", TABLE_VEHICLE)
        val vehicleList = ArrayList<Vehicle>()
        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_ID))
                val year = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_YEAR))
                val makeAndModel = cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_MAKE_MODEL))
                val purchasePrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_VEHICLE_PURCHASE_PRICE))
                val isNew = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_IS_NEW)) == 1
                vehicleList.add(Vehicle(id, year, makeAndModel, purchasePrice, isNew))
            }while(cursor.moveToNext())
        }
        db.close()
        return vehicleList
    }

    fun addRepair(repairToAdd: Repair) : Long{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_REPAIR_VEHICLE_ID, repairToAdd.vId)
        cv.put(COL_REPAIR_DATE, repairToAdd.repairDate)
        cv.put(COL_REPAIR_COST, repairToAdd.repairCost)
        cv.put(COL_REPAIR_DESCRIPTION, repairToAdd.repairDescription)
        val result = db.insert(TABLE_REPAIR, null, cv)
        db.close()
        return result
    }

    fun findRepairByKeyword(keyword : String) : List<VehicleRepair>{
        val db = readableDatabase
        val query = String.format("SELECT * FROM %s INNER JOIN %s ON %s = %s WHERE %s LIKE '%%%s%%'",
            TABLE_VEHICLE, TABLE_REPAIR, COL_VEHICLE_ID, COL_REPAIR_VEHICLE_ID, COL_REPAIR_DESCRIPTION, keyword)
        val vehicleRepairList = ArrayList<VehicleRepair>()
        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            do{
                val vId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_ID))
                val year = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_YEAR))
                val makeAndModel = cursor.getString(cursor.getColumnIndexOrThrow(COL_VEHICLE_MAKE_MODEL))
                val purchasePrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_VEHICLE_PURCHASE_PRICE))
                val isNew = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VEHICLE_IS_NEW)) == 1
                val vehicle = Vehicle(vId, year, makeAndModel, purchasePrice, isNew)

                val rId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_REPAIR_ID))
                val vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_REPAIR_VEHICLE_ID))
                val repairDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_REPAIR_DATE))
                val repairCost = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_REPAIR_COST))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COL_REPAIR_DESCRIPTION))
                val repair = Repair(rId, vehicleId, repairDate, repairCost, description)

                vehicleRepairList.add(VehicleRepair(vehicle, repair))
            }while(cursor.moveToNext())
        }
        db.close()
        return vehicleRepairList
    }

    companion object {
        private var instance: DBHelper? = null
        //Database Info
        private const val DB_NAME = "Repair Shop"
        private const val DB_VERSION : Int = 1

        //Vehicle Table
        private const val TABLE_VEHICLE = "Vehicle"
        private const val COL_VEHICLE_ID = "vid"
        private const val COL_VEHICLE_YEAR = "year"
        private const val COL_VEHICLE_MAKE_MODEL = "make_model"
        private const val COL_VEHICLE_PURCHASE_PRICE = "purchase_price"
        private const val COL_VEHICLE_IS_NEW = "is_new"

        //Repair Table
        private const val TABLE_REPAIR = "Repair"
        private const val COL_REPAIR_ID = "rid"
        private const val COL_REPAIR_VEHICLE_ID = "vehicle_vid"
        private const val COL_REPAIR_DATE = "repair_date"
        private const val COL_REPAIR_COST = "repair_cost"
        private const val COL_REPAIR_DESCRIPTION = "repair_description"

        fun getInstance(context: Context): DBHelper {
            if(instance == null) {
                instance = DBHelper(context)
            }
            return instance!!
        }
    }
}