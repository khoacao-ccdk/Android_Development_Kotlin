package com.codycao.repairshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.codycao.repairshop.database.DBHelper
import com.codycao.repairshop.database.Vehicle
import com.codycao.repairshop.databinding.ActivityAddVehicleBinding

class AddVehicleActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddVehicleBinding

    val button_add_vehicle_clickListener = View.OnClickListener {
        val year = binding.edittextYear.text.toString()
        val makeAndModel = binding.edittextMakeModel.text.toString()
        val purchasePrice = binding.edittextPrice.text.toString()
        val isNew = binding.checkboxIsNew.isChecked
        if(year != "" && makeAndModel != "" && purchasePrice != ""){
            val intYear = year.toInt()
            val doublePurchasePrice = purchasePrice.toDouble()
            val vehicleToAdd = Vehicle(intYear, makeAndModel, doublePurchasePrice, isNew)
            val result = DBHelper.getInstance(AddVehicleActivity@this).addVehicle(vehicleToAdd)
            if(!result.equals(-1)) {
                Toast.makeText(AddVehicleActivity@this,
                    "Vehicle Added Successfully!",
                    Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
        else{
            val toast = Toast.makeText(AddVehicleActivity@this,
                "Please fill in all information",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddVehicle.setOnClickListener(button_add_vehicle_clickListener)
    }
}