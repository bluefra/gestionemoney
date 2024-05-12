package gestionemoney.compose.controller

import android.util.Log
import gestionemoney.compose.model.Category
import gestionemoney.compose.model.Expense
import gestionemoney.compose.model.User
import java.util.Date

class UserWrapper private constructor(){
    var user: User? = null
    /**
     * usato implementare il pattern Singleton nella classe UserWrapper
     */
    companion object {
        private var instance: UserWrapper? = null

        fun getInstance(): UserWrapper {
            return instance ?: synchronized(this) {
                instance ?: UserWrapper().also { instance = it }
            }
        }
    }
    fun isSet(): Boolean {
        return user != null
    }
    fun updateUser(newUser: User) {
        user = newUser
    }

    fun getUID(): String? {
        if(!isSet()) {
            return null
        }
        return user!!.getUID()
    }

    fun addCategory(name: String): Boolean {
        if(!isSet()) {
            return false
        }
        if(hasCategory(name)) {
            return false
        }
        user!!.addCategory(Category(name))
        Log.w("UserWrapper","category added: $name")
        return true
    }

    fun hasCategory(name: String): Boolean {
        if(!isSet()) {
            return false
        }
        user!!.getList().forEach {
            if(it.getName() == name) {
                return true
            }
        }
        return false
    }

    fun getCategory(name: String): Category? {
        if(!isSet()) {
            return null
        }
        user!!.getList().forEach {
            if(it.getName() == name) {
                return it
            }
        }
        return null
    }
    fun addExpense(categoryName: String, date: Date, value: Double){
        val category: Category? = getCategory(categoryName)
        category?.addExpenses(Expense(date, value))
    }

    fun getCategoryList(): List<Category>? {
        if(!isSet()) {
            return null
        }
        return user!!.getList()
    }

    fun createCategories(names: Array<String>): Boolean {
        var added: Boolean = false //->added = true se almeno una categoria è stata aggiunta
        names.forEach {
            added = addCategory(it) || added
        }
        return added
    }

    override fun toString(): String {
        return user.toString()
    }
}