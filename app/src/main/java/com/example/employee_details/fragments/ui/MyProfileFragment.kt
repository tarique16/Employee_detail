package com.example.employee_details.fragments.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.employee_details.R
import com.example.employee_details.Util
import com.example.employee_details.databinding.FragmentHomePageBinding
import com.example.employee_details.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {
    lateinit var binding: FragmentMyProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        val view = binding.root
//        binding.txtName.setText()
        val username = Util.getUserName(requireContext())
        val password = Util.getPassword(requireContext())
        binding.txtName.setText(username)
        binding.txtPassword.setText("✱✱✱✱")

        binding.imgShowView.setOnClickListener {
            binding.imgShowView.visibility = View.GONE
            binding.imgShowHide.visibility = View.VISIBLE
            binding.txtPassword.setText(password)
//            "●●●●""****"
        }
        binding.imgShowHide.setOnClickListener {
            binding.imgShowHide.visibility = View.GONE
            binding.imgShowView.visibility = View.VISIBLE
            binding.txtPassword.setText("✱✱✱✱")
        }
//        binding.txtName.text = preference.getString("Username")
        return view
    }

}