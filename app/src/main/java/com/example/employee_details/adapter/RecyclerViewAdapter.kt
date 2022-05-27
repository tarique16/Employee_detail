package com.example.employee_details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_details.data.model.Employee
import com.example.employee_details.data.model.ListData
import com.example.employee_details.databinding.RecyclerRowBinding

class RecyclerViewAdapter(var context: Context, private val listener: AdapterListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    private var empListData = emptyList<Employee>()


    interface AdapterListener {
        fun updateData(employee: Employee)
        fun getCurrentItem(employee: Employee)
        fun deleteEmp(employee: Employee)
    }

    inner class ViewHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(employee: Employee) {
            binding.tvName.text = employee.name
            binding.tvAge.text = employee.age.toString()
            binding.rowLayout.setOnClickListener {
                listener.getCurrentItem(employee)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return empListData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.onBind(empListData[position])
    }

    fun setData(employee: List<Employee>) {
        this.empListData = employee
        notifyDataSetChanged()
    }
    fun removeAt(position: Int) {
//        empListData.removeAt(position)
        notifyItemRemoved(position)
    }

    interface ClickListener {
        fun onItemClick(dataModel: ListData)
    }
}