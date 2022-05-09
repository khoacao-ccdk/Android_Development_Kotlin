package com.codycao.repairshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.codycao.repairshop.database.DBHelper
import com.codycao.repairshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddRepair.setOnClickListener(this)
        binding.buttonAddVehicle.setOnClickListener(this)
        binding.buttonSearchRepairs.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent: Intent = when (v!!.id){
            R.id.button_add_repair -> {
                Intent(this, AddRepairActivity::class.java)
            }
            R.id.button_add_vehicle -> {
                Intent(this, AddVehicleActivity::class.java)
            }
            else -> {
                Intent(this, SearchRepairActivity::class.java)
            }
        }
        startActivity(intent)
    }
}