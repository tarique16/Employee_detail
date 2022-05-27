package com.example.employee_details.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.employee_details.data.daos.CredentialDao
import com.example.employee_details.data.daos.EmployeeDao
import com.example.employee_details.data.model.Credential
import com.example.employee_details.data.model.Employee

/*

@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeeDB: RoomDatabase() {


    abstract fun employeeDao(): EmployeeDao

    private class EmployeeDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.employeeDao())
                }
            }
        }

        suspend fun populateDatabase(employeeDao: EmployeeDao) {
            // Delete all content here.
            //employeeDao.deleteAll()

            // Add sample words.
            var employee = Employee(1,1,"Tarique",25)
            employeeDao.insertAllEmployee(employee)
            employee = Employee(2,1,"A",21)
            employeeDao.insertAllEmployee(employee)

            // TODO: Add your own words!
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: EmployeeDB? = null

        fun getDatabase(context: Context): EmployeeDB{
            val tempInstance = INSTANCE
            if(tempInstance != null)(
                    return tempInstance
            )
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDB::class.java,
                    "employee_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}*/

@Database(
    entities = [Employee::class, Credential::class],
    version = 3,
    exportSchema = false
)
abstract class EmployeeDB : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun credentialDao(): CredentialDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDB? = null

        fun getDatabase(context: Context): EmployeeDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDB::class.java,
                    "employee_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}