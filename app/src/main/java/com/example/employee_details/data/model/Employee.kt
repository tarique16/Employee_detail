package com.example.employee_details.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo val name:String,
    @ColumnInfo val age:Int)