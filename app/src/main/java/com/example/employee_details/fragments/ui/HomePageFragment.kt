package com.example.employee_details.fragments.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.digisaarthi.utils.RecyclerItemTouchHelper1
import com.example.employee_details.R
import com.example.employee_details.adapter.RecyclerViewAdapter
import com.example.employee_details.data.model.Employee
import com.example.employee_details.data.model.ListData
import com.example.employee_details.data.viewmodel.EmployeeViewModel
import com.example.employee_details.databinding.FragmentHomePageBinding
import com.example.employee_details.databinding.ItemEmployeeAddBinding
import com.example.employee_details.databinding.ItemEmployeeUpdateBinding
import kotlinx.coroutines.launch


class HomePageFragment : Fragment(), RecyclerViewAdapter.ClickListener,
    RecyclerItemTouchHelper1.RecyclerItemTouchHelperListener1 {

    private lateinit var mEmployeeViewModel: EmployeeViewModel
    lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding.recView
        val swipeHandler = object : RecyclerItemTouchHelper1(0, ItemTouchHelper.LEFT, this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                askForDelete(recyclerView, viewHolder, position)
                /*val adapter = recyclerView.adapter as RecyclerViewAdapter
                adapter.removeAt(viewHolder.adapterPosition)*/
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        val recyclerAdapter = RecyclerViewAdapter(requireContext(), adapterListenerImpl)

        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mEmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        mEmployeeViewModel.readAllEmployee.observe(viewLifecycleOwner, Observer { employee ->
            recyclerAdapter.setData(employee)
        }
        )
        binding.ordersRefresher.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.ordersRefresher.isRefreshing = false
            lifecycleScope.launch {
                mEmployeeViewModel.readAllEmployee.observe(viewLifecycleOwner,
                    Observer { employee ->
                        recyclerAdapter.setData(employee)
                    })
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNew.setOnClickListener {

            val insertBinding = ItemEmployeeAddBinding.inflate(layoutInflater)
            val etName=insertBinding.etName
            val etAge=insertBinding.etAge
            val tvAdd = insertBinding.tvAdd
            val customDialog = Dialog(requireContext())
            customDialog.setContentView(insertBinding.root)
            customDialog.window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparent
                    )
                )
            )
            customDialog.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            customDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            customDialog.show()

            tvAdd.setOnClickListener {
                val insertName = etName.text.toString()
                val insertAge = etAge.text

                etName.addTextChangedListener{
                    etName.setBackgroundResource(R.drawable.shape_border_background3)
                }
                etAge.addTextChangedListener{
                    etAge.setBackgroundResource(R.drawable.shape_border_background3)
                }
                when{
                    insertName.isNotEmpty() && insertAge.isEmpty() -> {
                        etAge.error = "Please enter age"
                        etAge.setBackgroundResource(R.drawable.shape_error)
                    }
                    insertName.isEmpty() && insertAge.isNotEmpty() -> {
                        etName.error = "Please enter name"
                        etName.setBackgroundResource(R.drawable.shape_error)
                    }
                    !(insertName.isEmpty() && insertAge.isEmpty()) -> {
                        insertDataToDatabase(etName,etAge)
                        customDialog.dismiss()
                    }
                    else -> {
                        redLl(etName,etAge)
                    }
                }
            }
        }

    }

    private fun insertDataToDatabase(et1: EditText,et2: EditText) {
        val name = et1.text.toString()
        val age = et2.text

        if (inputCheck(name, age)) {
            // Create User Object
            val employee = Employee(0, name, Integer.parseInt(age.toString()))
            // Add Data to Database
            mEmployeeViewModel.addEmployee(employee)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
        } else {
            redLl(et1,et2)
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun inputCheck(name: String, age: Editable): Boolean {
        return !(name.isEmpty() && age.isEmpty())
    }

    private fun redLl(et1:EditText,et2:EditText){
        et1.setBackgroundResource(R.drawable.shape_error)
        et1.error = "Please enter name"
        et2.setBackgroundResource(R.drawable.shape_error)
        et2.error = "Please enter age"
        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
    }

    private val adapterListenerImpl = object : RecyclerViewAdapter.AdapterListener {
        override fun updateData(employee: Employee) {
            mEmployeeViewModel.updateEmployee(employee)
        }

        override fun getCurrentItem(employee: Employee) {

            val customDialog = Dialog(requireContext())
            val updateBinding = ItemEmployeeUpdateBinding.inflate(layoutInflater)
            customDialog.setContentView(updateBinding.root)
            customDialog.window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparent
                    )
                )
            )
            customDialog.window?.setLayout(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            customDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            val uName = updateBinding.etUpdateName
            val uAge = updateBinding.etUpdateAge
            val update = updateBinding.tvUpdate
            customDialog.show()

            update.setOnClickListener {
                val updName = uName.text.toString()

                val updAge = uAge.text

                uName.addTextChangedListener {
                    uName.setBackgroundResource(R.drawable.shape_border_background3)
                }
                uAge.addTextChangedListener {
                    uAge.setBackgroundResource(R.drawable.shape_border_background3)
                }
                when {
                    updName.isNotEmpty() && updAge.isEmpty() -> {
                        uAge.error = "Please enter age"
                        uAge.setBackgroundResource(R.drawable.shape_error)
                    }
                    updName.isEmpty() && updAge.isNotEmpty() -> {
                        uName.error = "Please enter name"
                        uName.setBackgroundResource(R.drawable.shape_error)
                    }
                    !(updName.isEmpty() && updAge.isEmpty()) -> {
                        val updatedUser =
                            Employee(employee.id, updName, Integer.parseInt(updAge.toString()))
                        uName.setBackgroundResource(R.drawable.shape_border_background1)
                        uAge.setBackgroundResource(R.drawable.shape_border_background1)
                        mEmployeeViewModel.updateEmployee(updatedUser)
                        customDialog.dismiss()
                        Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        uName.error = "Please enter name"
                        uName.setBackgroundResource(R.drawable.shape_error)
                        uAge.error = "Please enter age"
                        uAge.setBackgroundResource(R.drawable.shape_error)
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
            uName.post {
                uName.setText(employee.name)
                uName.setSelection(employee.name.length)
                uAge.setText(employee.age.toString())
                uAge.setSelection(employee.age.toString().length)
            }

        }

        override fun deleteEmp(employee: Employee) {
            TODO("Not yet implemented")
        }

    }


    fun askForDelete(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val name =
            (recyclerView.findViewHolderForAdapterPosition(position)!!.itemView.findViewById(R.id.tv_name) as TextView).text.toString()
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->

                val adapter = recyclerView.adapter as RecyclerViewAdapter
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        mEmployeeViewModel.deleteEmpAt(name)
                        adapter.removeAt(viewHolder.adapterPosition)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        lifecycleScope.launch {
                            mEmployeeViewModel.readAllEmployee.observe(viewLifecycleOwner,
                                Observer { employee ->
                                    adapter.setData(employee)
                                })
                        }
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure?")
        builder.setPositiveButton("Yes", dialogClickListener)
        builder.setNegativeButton("No", dialogClickListener).show()
    }


    override fun onItemClick(dataModel: ListData) {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        TODO("Not yet implemented")
    }

}
