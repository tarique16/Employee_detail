package com.example.employee_details

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.employee_details.data.viewmodel.EmployeeViewModel
import com.example.employee_details.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var mEmployeeViewModel: EmployeeViewModel
    lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("Cred", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        val name = sharedPref.getString("Username", null)
        if (name.isNullOrEmpty()) {
            mEmployeeViewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
            mEmployeeViewModel.getCredentials(binding.etUsername.text.toString(),
                binding.etPassword.text.toString())
            mEmployeeViewModel.readCredential.observe(this) { list ->
                list.let {
                    if (list.isNotEmpty()) {
//                        var new_user: Credential
                        val name = binding.etUsername
                        val empName = name.text.toString().trim()
                        val empPassword = binding.etPassword.text.toString()
                        Util.setUserName(applicationContext, empName)
                        Util.setPassword(applicationContext, empPassword)
                        editor.putString("Username", empName)
                        editor.apply()
                        val intent = Intent(this, TabbedHomePage::class.java)
                        startActivity(intent)
                        this.finish()
                    } else {
                        Toast.makeText(this, "No Record Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }


            val uName = binding.etUsername
            val uPass = binding.etPassword

            uName.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

                }

                override fun beforeTextChanged(
                    arg0: CharSequence,
                    arg1: Int,
                    arg2: Int,
                    arg3: Int,
                ) {

                }

                override fun afterTextChanged(et: Editable) {
                    uName.setBackgroundResource(R.drawable.shape_border_background3)
                    var s = et.toString()
                    if (s != s.uppercase() && s.length == 1) {
                        s = s.uppercase()
                        uName.setText(s)
                        uName.setSelection(uName.length())
                    }
                }
            })
            uPass.addTextChangedListener {
                uPass.setBackgroundResource(R.drawable.shape_border_background3)
            }
            binding.btnLogin.setOnClickListener {
                if (validation()) {
                    mEmployeeViewModel.getCredentials(uName.text.toString(), uPass.text.toString())
                }
                /*if (uName.text.isEmpty()) {
                    uName.setBackgroundResource(R.drawable.shape_error)
                    uName.error = "Enter Username"
                } else if (uPass.text.isEmpty()) {
                    uPass.setBackgroundResource(R.drawable.shape_error)
                    uPass.error = "Enter Password"
                } else {
                    mEmployeeViewModel.getCredentials(uName.text.toString(), uPass.text.toString())
                }*/
            }
        } else {
            val intent = Intent(this, TabbedHomePage::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun validation(): Boolean {
        val uName = binding.etUsername
        val uPass = binding.etPassword
        var checkFlag = true
        when {
            uName.text.isEmpty() && uPass.text.isNotEmpty() -> {
                uName.setBackgroundResource(R.drawable.shape_error)
                uName.error = "Enter Username"
                checkFlag = false
            }
            uName.text.isNotEmpty() && uPass.text.isEmpty() -> {
                uPass.setBackgroundResource(R.drawable.shape_error)
                uPass.error = "Enter Password"
                checkFlag = false
            }
            uName.text.isEmpty() && uPass.text.isEmpty() -> {
                uName.setBackgroundResource(R.drawable.shape_error)
                uName.error = "Enter Username"
                uPass.setBackgroundResource(R.drawable.shape_error)
                uPass.error = "Enter Password"
                checkFlag = false
            }
        }
        return checkFlag
    }


}