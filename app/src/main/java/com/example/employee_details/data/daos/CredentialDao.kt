package com.example.employee_details.data.daos

import androidx.room.*
import com.example.employee_details.data.model.Credential

@Dao
interface CredentialDao {
    @Insert
    suspend fun insertEmployeeCredential(credential: Credential)

    @Update
    suspend fun updateEmployeeCredential(credential: Credential)

//    @Query("Select * from Credential")
//    fun getEmployeeCredential(): LiveData<Credential>

    @Query("Select * from credential where username like :username and password like :password")
    fun getCredentials(username:String, password: String):List<Credential>

/*    @Query("Select cast(password as Int) as password from credential where password like :password")
    fun getPassword(password:String):LiveData<Credential>*/
/*    @Query("Select * from Employee where id in : id")
    fun getABoutEmp(id :Int)*/

    @Delete
    fun deleteEmployeeCredential(credential: Credential)
}