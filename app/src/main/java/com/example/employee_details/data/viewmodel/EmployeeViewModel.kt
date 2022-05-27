package com.example.employee_details.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.employee_details.data.EmployeeDB
import com.example.employee_details.data.model.Credential
import com.example.employee_details.data.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EmployeeRepository
    val readAllEmployee: LiveData<List<Employee>>
    val readCredential: MutableLiveData<List<Credential>>
//    var readEmployeeCredential: LiveData<Credential>

    init {
        val employeeDao = EmployeeDB.getDatabase(application).employeeDao()
        val credentialDao = EmployeeDB.getDatabase(application).credentialDao()
        repository = EmployeeRepository(employeeDao, credentialDao)
        readAllEmployee = repository.getAllEmployee
        readCredential=MutableLiveData()
//        readEmployeeCredential = repository.getEmployeeCredential

    }

    fun getCredentials(username:String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
            readCredential.postValue(repository.getEmpUsername(username, password))
        }
    }
    /*fun getPassword(password:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEmpPassword(password)
        }
    }*/
    fun addCredential(credential: Credential){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEmployeeCredential(credential)
        }
    }

    fun updateCredential(credential: Credential){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEmployeeCredential(credential)
        }
    }

    fun deleteCredential(credential: Credential){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmployeeCredential(credential)
        }
    }

    fun addEmployee(employee: Employee) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEmployee(employee)
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEmployee(employee)
        }
    }

    fun deleteEmployee(employee: Employee) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmployee(employee)
        }
    }

    fun deleteEmpAt(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmpAt(name)
        }
    }

}