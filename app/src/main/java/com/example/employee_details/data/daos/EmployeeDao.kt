package com.example.employee_details.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.employee_details.data.model.Employee

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertAllEmployee(employee: Employee)

    @Update
    suspend fun updateAllEmployee(employee: Employee)

    @Delete
    fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM Employee ORDER BY name ASC")
    fun getAllEmployee(): LiveData<List<Employee>>

    @Query("DELETE FROM employee WHERE name like :name")
    fun deleteEmpAt(name: String)

/*    @Query("Select * from Employee where id in : id")
    fun getABoutEmp(id :Int)*/


}