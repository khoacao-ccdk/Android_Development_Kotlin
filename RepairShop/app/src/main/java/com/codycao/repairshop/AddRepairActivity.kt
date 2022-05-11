package com.codycao.repairshop

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.codycao.repairshop.database.DBHelper
import com.codycao.repairshop.database.Repair
import com.codycao.repairshop.database.Vehicle
import com.codycao.repairshop.databinding.ActivityAddRepairBinding
import java.lang.StringBuilder
import java.time.LocalDateTime

class AddRepairActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddRepairBinding
    private lateinit var vehicleList : List<Vehicle>

    private val button_add_repair_clickListener = View.OnClickListener {
        val repairDate = binding.edittextRepairDate.text.toString()
        val repairCost = binding.edittextRepairCost.text.toString()
        val description = binding.edittextRepairDescription.text.toString()
        if(repairDate != "" && repairCost != "" && description != ""){
            val doubleRepairCost = repairCost.toDouble()
            val vehicle = binding.spinnerVehicles.selectedItem as Vehicle
            val vId = vehicle.vId
            val result = DBHelper.getInstance(this).addRepair(Repair(
                            vId, repairDate, doubleRepairCost, description))
            if(!result.equals(-1)){
                Toast.makeText(AddVehicleActivity@this,
                    "Repair Added Successfully!",
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

    private val edittext_repair_date_clickListener = View.OnClickListener{
        val today = LocalDateTime.now()
        DatePickerDialog(AddVehicleActivity@this,
            { datePicker: DatePicker, year: Int, month: Int, dateOfMonth: Int ->
                val month = month + 1
                val dateString = StringBuilder()
                    .append("$year-")
                    .append(if(month < 10) "0$month" else month).append("-")
                    .append(if(dateOfMonth < 10) "0$dateOfMonth" else dateOfMonth)
                    .toString()
                binding.edittextRepairDate.setText(dateString)
            },
            today.year, today.monthValue - 1, today.dayOfMonth).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRepairBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vehicleList = DBHelper.getInstance(this).getAllVehicle()
        val adapter = ArrayAdapter(this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    vehicleList)
        binding.spinnerVehicles.adapter = adapter
        binding.buttonAddRepair.setOnClickListener(button_add_repair_clickListener)
        binding.edittextRepairDate.setOnClickListener(edittext_repair_date_clickListener)
    }
}