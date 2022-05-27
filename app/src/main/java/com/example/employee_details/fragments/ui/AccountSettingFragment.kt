package com.example.employee_details.fragments.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.employee_details.MainActivity
import com.example.employee_details.databinding.FragmentAccountSettingBinding

class AccountSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bind = FragmentAccountSettingBinding.inflate(layoutInflater)
        val logOut=bind.btnLogout
        val preference = requireActivity().getSharedPreferences("Cred", Context.MODE_PRIVATE)
        logOut.setOnClickListener {
            Toast.makeText(requireActivity(),"Logging Out", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(),MainActivity::class.java)
            val editor : SharedPreferences.Editor = preference.edit()
            editor.clear()
            editor.apply()
            startActivity(intent)
            requireActivity().finish()
        }
        return bind.root
    }

}