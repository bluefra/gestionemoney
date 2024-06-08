package gestionemoney.compose.controller

import gestionemoney.compose.model.Category
import gestionemoney.compose.model.Expense
import gestionemoney.compose.model.User
import java.util.Date

/**
 * used to add interface to the model User class
 * @see User
 */
class UserWrapper private constructor(){
    private var user: User? = null
    /**
     * used to implements the singleton pattern for the class
     */
    companion object {
        private var instance: UserWrapper? = null

        fun getInstance(): UserWrapper {
            return instance ?: synchronized(this) {
                instance ?: UserWrapper().also { instance = it }
            }
        }
    }

    /**
     * @return true if the object has been initialized properly
     */
    fun isSet(): Boolean {
        return user != null
    }

    /**
     * used to set the user object that the class would envelop
     */
    fun updateUser(newUser: User) {
        user = newUser
    }

    /**
     * if the user is set, it will return the userID, associated with it
     * otherwise it will return null
     */
    fun getUID(): String? {
        if(!isSet()) {
            return null
        }
        return user!!.getUID()
    }

    /**
     * ad a category item to the wrapped user, if nothing is passed, it will set the default image
     * to ""
     * @return true if the category has been properly added, false otherwise
     */
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

    /**
     * @return true if the user has at least a category
     */
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

    /**
     * @return the category associated with the name passed as parameter
     * if the category isn't found or the user is not set, it will return null
     */
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

    /**
     * it will construct an expense from the parameter and if it will find the category associated
     * with the passed name, it will add the created expense to the founded category
     */
    fun addExpense(categoryName: String, name: String, date: Date, value: Double){
        val category: Category? = getCategory(categoryName)
        val expense = Expense(date, value, name)
        category?.addExpenses(expense)
    }

    /**
     * if the use is set, it will return the category list associated with the user, otherwise
     * it will return an empty list
     */
    fun getCategoryList(): List<Category> {
        if(!isSet()) {
            return listOf()
        }
        return user!!.getList()
    }

    /**
     * @return the ordered list of category associated with the user, ordered by name using the
     * order passed by parameter ASC, DESC.
     * if the user isn't set, it will return an empty list
     */
    fun getOrderedListByName(order: Category.ORDER): List<Category> {
        if(!isSet()) {
            return listOf()
        }
        return user!!.orderByName(order)
    }

    /**
     * @return the ordered list of category associated with the user, ordered by value
     * (value = sum(expense.value)) using the order passed by parameter ASC, DESC.
     * if the user isn't set, it will return an empty list
     */
    fun getOrderedListByValue(order: Category.ORDER): List<Category> {
        if(!isSet()) {
            return listOf()
        }
        return user!!.orderByValue(order)
    }

    /**
     * @return a list of all the name of the category that the user has.
     * if the user hasn't any associated category, it will return an empty list
     */
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

    /**
     * create multiple categories and add them to the user categories list.
     * it will return true if at least a category is added.
     * if the two arrays have different size the function wont add create category
     */
    fun createCategories(names: Array<String>, images: Array<String>): Boolean {
        var added = false //->added = true se almeno una categoria è stata aggiunta
        if(names.size == images.size) {
            for(i in names.indices) {
                added = addCategory(names[i],images[i]) || added
            }
        }
        return added
    }

    /**
     * @return the number of category that the user has
     */
    fun getCategoryNumber(): Int{
        if(!isSet()) { return 0 }
        return user!!.getList().size
    }

    /**
     * @return the avg expense value per category
     */
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

    /**
     * @return the total amount spent by the user
     */
    fun getTotalExpenseNumber(): Int {
        if(!isSet()) { return 0 }
        var tot = 0
        user!!.getList().forEach {
            tot += it.getList().size
        }
        return tot
    }
    /**
     * @return the average number of character used by the categories names
     */
    fun getCategoryAvgCharacter(): Double {
        if(!isSet()) { return 0.0 }
        var totalChar = 0.0
        user!!.getList().forEach{
            totalChar += it.getName().length
        }
        return totalChar / user!!.getList().size
    }
    /**
     * @return the average number of character used by the expenses names
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

    /**
     * @return the average expense number per category
     */
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

    /**
     * reset the class, used for log out.
     * if there are no other saves method, the user data will be lose
     */
    fun close() {
        instance = null
        user = null
    }
}