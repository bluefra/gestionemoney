package gestionemoney.compose.model

import android.util.Log
import java.util.Date


class Category (private val name: String){
    private var expensesList: MutableList<Expense> = mutableListOf()
    private var imageURI: String? = null
    fun addExpenses(expense: Expense) {
        Log.w("Category", "adding $expense")
        expensesList.add(expense)
    }
    fun compareName(c1: Category): Int {
        return name.compareTo(c1.name)
    }
    fun compareName(comparedName: String): Int {
        return name.compareTo(comparedName)
    }

    fun orderByValue(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.confrontValue(e1) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.confrontValue(e1) }
        }
    }

    fun orderByDate(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.confrontDate(e1) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.confrontDate(e1) }
        }
    }

    fun getList(): List<Expense> {
        return expensesList
    }

    fun getName(): String {
        return name
    }

    fun setImage(uri: String) {
        imageURI = uri
    }
    fun getImageURI(): String? {
        return imageURI
    }

    fun getDBname(): String {
        var nameDB = name
        if(imageURI != null) {
            nameDB += Companion.DBtoken + imageURI
        }
        return nameDB
    }

    fun GetTotalExpences(): Double {
        var tot: Double = 0.0
        expensesList.forEach {
            tot += it.getValue()
        }
        return tot
    }
    fun toHashmap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        expensesList.forEach() {
            map[it.getDate().toString()] = it.getValue()
        }
        return map
    }

    fun loadFromHashmap(map: HashMap<String, Double>) {
        map.forEach {
            expensesList.add(Expense(Date(it.key), it.value))
        }
    }
    override fun toString(): String {
        var s = "$name:\t"
        if(imageURI != null) {
            s += "imageURI: $imageURI\n"
        }
        expensesList.forEach() {
            s+= it.toString()
        }
        return s
    }
    enum class ORDER{
        ASC,
        DEC
    }

    companion object {
        private const val DBtoken: String = ":"

        fun loadCategoryFromDB(name: String): Category {
            val results = name.split(DBtoken)
            val category = Category(results[0])
            if(results.size == 2) {
                category.setImage(results[1])
            }
            return category
        }
    }
}