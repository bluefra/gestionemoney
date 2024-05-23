package gestionemoney.compose.controller

import android.util.Log
import gestionemoney.compose.model.Category
import gestionemoney.compose.model.Expense
import gestionemoney.compose.model.User
import java.util.Date
import kotlin.math.round

class UserWrapper private constructor(){
    private var user: User? = null
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

    fun addCategory(name: String, imageUri: String = ""): Boolean {
        if(!isSet()) {
            return false
        }
        if(hasCategory(name)) {
            return false
        }
        user!!.addCategory(Category(name, imageUri))
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
    fun addExpense(categoryName: String, name: String, date: Date, value: Double){
        val category: Category? = getCategory(categoryName)
        val expense = Expense(date, value, name)
        category?.addExpenses(expense)
    }

    fun getCategoryList(): List<Category>? {
        if(!isSet()) {
            return null
        }
        return user!!.getList()
    }


    fun getOrderedListByName(order: Category.ORDER): List<Category> {
        if(!isSet()) {
            return listOf()
        }
        return user!!.orderByName(order)
    }

    fun getOrderedListByValue(order: Category.ORDER): List<Category> {
        if(!isSet()) {
            return listOf()
        }
        return user!!.orderByValue(order)
    }

    fun getCategoriesNames(): List<String> {
        val list: MutableList<String> = mutableListOf()
        if(!isSet()) {
            return list
        }
        user!!.getList().forEach {
            list.add(it.getName())
        }
        return list
    }

    fun createCategories(names: Array<String>, images: Array<String>): Boolean {
        var added = false //->added = true se almeno una categoria è stata aggiunta
        if(names.size == images.size) {
            for(i in names.indices) {
                added = addCategory(names[i],images[i]) || added
            }
        }
        return added
    }

    fun getCategoryNumber(): Int{
        if(!isSet()) { return 0 }
        return user!!.getList().size
    }

    fun getAvgExpenseValue(): Double {
        if(!isSet()) { return 0.0 }
        var totalChar = 0.0
        val categoryList = user!!.getList()
        categoryList.forEach{ cat ->
            cat.getList().forEach { exp ->
                totalChar += exp.getValue()
            }

        }
        return totalChar / categoryList.size
    }

    fun getTotalExpenseNumber(): Int {
        if(!isSet()) { return 0 }
        var tot = 0
        user!!.getList().forEach {
            tot += it.getList().size
        }
        return tot
    }
    /*
    restituisce il numero medio di caratteri utilizzati per categoria
     */
    fun getCategoryAvgCharacter(): Double {
        if(!isSet()) { return 0.0 }
        var totalChar = 0.0
        user!!.getList().forEach{
            totalChar += it.getName().length
        }
        return totalChar / user!!.getList().size
    }
    /*
    restituisce il numero medio di caratteri utilizzati per spesa
     */
    fun getExpenseAvgCharacter(): Double {
        if(!isSet()) { return 0.0 }
        var totalChar = 0.0
        val categoryList = user!!.getList()
        var expenseNumber = 0
        categoryList.forEach{ cat ->
            cat.getList().forEach { exp ->
                totalChar += exp.getName().length
                expenseNumber++
            }
        }
        return totalChar / expenseNumber
    }
    fun getAvgExpenseNumber(): Double {
        if(!isSet()) { return 0.0 }
        var tot = 0
        user!!.getList().forEach {
            tot += it.getList().size
        }
        return tot.toDouble() / user!!.getList().size
    }

    override fun toString(): String {
        return user.toString()
    }

    fun close() {
        instance = null
        user = null
    }
}