package com.example.employee_details.data.viewmodel

import androidx.lifecycle.LiveData
import com.example.employee_details.data.daos.CredentialDao
import com.example.employee_details.data.daos.EmployeeDao
import com.example.employee_details.data.model.Credential
import com.example.employee_details.data.model.Employee

class EmployeeRepository(private val employeeDao: EmployeeDao, private val credentialDao: CredentialDao) {

    suspend fun insertEmployeeCredential(credential: Credential) {
        credentialDao.insertEmployeeCredential(credential)
    }

    suspend fun updateEmployeeCredential(credential: Credential) {
        credentialDao.updateEmployeeCredential(credential)
    }

    suspend fun deleteEmployeeCredential(credential: Credential) {
        credentialDao.deleteEmployeeCredential(credential)
    }

    fun getEmpUsername(username:String,password:String): List<Credential> = credentialDao.getCredentials(username,password)

    /*fun getEmpPassword(password:String): LiveData<Credential> {
        return credentialDao.getPassword(password)
    }*/

    suspend fun insertEmployee(employee: Employee) {
        employeeDao.insertAllEmployee(employee)
    }

    suspend fun updateEmployee(employee: Employee) {
        employeeDao.updateAllEmployee(employee)
    }

    suspend fun deleteEmployee(employee: Employee) {
        employeeDao.deleteEmployee(employee)
    }

    suspend fun deleteEmpAt(name: String){
        employeeDao.deleteEmpAt(name)
    }

    val getAllEmployee: LiveData<List<Employee>> = employeeDao.getAllEmployee()


}