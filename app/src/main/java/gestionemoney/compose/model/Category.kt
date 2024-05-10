package gestionemoney.compose.model

import java.util.Date


class Category (private val name: String){
    private var expensesList: MutableList<Expense> = mutableListOf()

    fun addExpenses(expense: Expense) {
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
        var s: String = "$name:\t"
        expensesList.forEach() {
            s+= it.toString()
        }
        return s
    }
    enum class ORDER{
        ASC,
        DEC
    }
}