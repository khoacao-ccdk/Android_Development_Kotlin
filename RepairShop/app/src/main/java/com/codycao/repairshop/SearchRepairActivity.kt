package com.codycao.repairshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codycao.repairshop.database.DBHelper
import com.codycao.repairshop.database.ListViewVehicleRepair
import com.codycao.repairshop.database.VehicleRepair
import com.codycao.repairshop.databinding.ActivitySearchRepairBinding

class SearchRepairActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchRepairBinding

    private val button_find_repair_clickListener = View.OnClickListener {
        val keyword = binding.edittextSearchPhrase.text.toString()
        val vehicleRepairList = DBHelper.getInstance(SearchRepairActivity@this).findRepairByKeyword(keyword)
        val listViewVehicleRepair = ListViewVehicleRepair(SearchRepairActivity@this, vehicleRepairList)
        binding.listviewResults.adapter = listViewVehicleRepair
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRepairBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFindRepairs.setOnClickListener(button_find_repair_clickListener)
    }
}