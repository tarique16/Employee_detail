package com.example.employee_details.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credential")
data class Credential (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val username:String,
    val password:Int)