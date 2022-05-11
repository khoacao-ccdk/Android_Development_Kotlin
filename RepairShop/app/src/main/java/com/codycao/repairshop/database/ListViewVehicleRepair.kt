package com.codycao.repairshop.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.codycao.repairshop.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ListViewVehicleRepair() : BaseAdapter() {

    constructor(context: Context, vehicleRepairList: List<VehicleRepair>) : this() {
        this.vehicleRepairList = vehicleRepairList
        this.context = context
    }

    override fun getCount(): Int {
        return vehicleRepairList.size
    }

    override fun getItem(position: Int): Any {
        return vehicleRepairList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_results_row, null)
        }
        val vehicleRepair = vehicleRepairList[position]
        val text_year_make_model = view!!.findViewById<TextView>(R.id.text_year_make_model)
        val text_repair_description = view!!.findViewById<TextView>(R.id.text_repair_description)
        val text_repair_date = view!!.findViewById<TextView>(R.id.text_repair_date)
        val text_repair_cost = view!!.findViewById<TextView>(R.id.text_repair_cost)

        text_year_make_model.setText(vehicleRepair.v.toString())
        text_repair_description.setText(vehicleRepair.r.repairDescription)
        text_repair_cost.setText(vehicleRepair.r.repairCost.toString())

        val date = SimpleDateFormat("yyyy-mm-dd").parse(vehicleRepair.r.repairDate)
        text_repair_date.setText(SimpleDateFormat("mm/dd/yyyy").format(date))
        return view!!
    }

    lateinit var vehicleRepairList : List<VehicleRepair>
    lateinit var context : Context
}