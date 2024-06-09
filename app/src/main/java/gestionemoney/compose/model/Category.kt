package gestionemoney.compose.model

import android.util.Log

class Category (private val name: String, private var imageURI: String = ""){
    private var expensesList: MutableList<Expense> = mutableListOf()

    /**
     * add an Expense passed as a parameter to the expense list
     * <strong>warning</strong> the expense isn't copied
     */
    fun addExpenses(expense: Expense) {
        Log.w("Category", "adding $expense")
        expensesList.add(expense)
    }

    /**
     * @return 1 -> this.name > c1.name, 0 -> this.name == c1.name, -1 -> this.name > c1.name
     * oss: it isn't case sensitive
     */
    fun compareName(c1: Category): Int {
        return name.uppercase().compareTo(c1.name.uppercase())
    }
    /**
     * @return 1 -> this.name > comparedName, 0 -> this.name == comparedName, -1 -> this.name > comparedName
     * oss: it isn't case sensitive
     */
    fun compareName(comparedName: String): Int {
        return name.uppercase().compareTo(comparedName.uppercase())
    }

    /**
     * return List<Expense> ordered by value using the order passed as parameter: ASC, DESC
     */
    fun orderByValue(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.confrontValue(e1) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.confrontValue(e1) }
        }
    }
    /**
     * return List<Expense> ordered by date using the order passed as parameter: ASC, DESC
     */
    fun orderByDate(order: ORDER): List<Expense> {
        return when (order) {
            ORDER.ASC -> expensesList.sortedWith { e0, e1 -> e0.compareDate(e1.getDate()) }
            ORDER.DEC -> expensesList.sortedWith { e0, e1 -> -e0.compareDate(e1.getDate()) }
        }
    }
    /**
     * return List<Expense> as it is saved
     */
    fun getList(): List<Expense> {
        return expensesList
    }

    /**
     * return the name associated with the category object
     */
    fun getName(): String {
        return name
    }

    /**
     * set the image uri
     * <strong>warning</strong> it doesn't control the integrity of the uri
     */
    fun setImage(uri: String) {
        imageURI = uri
    }

    /**
     * return the image uri
     */
    fun getImageURI(): String {
        return imageURI
    }

    /**
     * it will build the name as the category should be stored in the db (DBname).
     * return DBname
     */
    fun getDBname(): String {
        var nameDB = name
        nameDB += DBtoken + imageURI
        return nameDB
    }

    /**
     * remove the expense passed as parameter from the list, if the expense isn't found it will do
     * nothing
     */
    fun deleteExpense(expense: Expense) {
        expensesList.remove(expense)
    }

    /**
     * @return the sum of value from all expenses saved
     */
    fun getTotalExpenses(): Double {
        var tot: Double = 0.0
        expensesList.forEach {
            tot += it.getValue()
        }
        return tot
    }

    /**
     * @return Expense matching the dateString passed as parameter, if the expense doesn't exist,
     * it will return null
     */
    fun getExpenseByString(dateString: String): Expense? {
        expensesList.forEach{
            Log.w("category expense delete log", it.toString())
            Log.w("category expense delete log", it.compareDateByString(dateString).toString())
            if(it.compareDateByString(dateString)) { return it }
        }
        return null
    }

    /**
     * @return the last expense added to the list as an Hashmap <String, any> -> <DB_name, value>,
     * if the expense list is empty, it will return null.
     */
    fun lastExpenseHashMap(): HashMap<String, Any>? {
        if(expensesList.size == 0) { return null }
        val map: HashMap<String, Any> = HashMap()
        val lastExpense = expensesList.last()
        map[lastExpense.getDBName()] = lastExpense.getValue()
        return map
    }
    /**
     * @return the expenses list as an Hashmap <String, any> -> <DB_name, value>
     */
    fun toHashmap(): HashMap<String, Any> {
        val map: HashMap<String, Any> = HashMap()
        expensesList.forEach {
            map[it.getDBName()] = it.getValue()
        }
        return map
    }
    /**
     * it will recreate the expenses list from the hashmap passed.
     * -> the key value of the hashmap must be the DB_name of each expense
     */
    fun loadFromHashmap(map: HashMap<String, Double>) {
        map.forEach {
            expensesList.add(Expense.loadExpenseFromDB(it.key, it.value))
        }
    }
    override fun toString(): String {
        var s = "$name:\t"
        s += "imageURI: $imageURI\n"
        expensesList.forEach {
            s+= it.toString()
        }
        return s
    }

    /**
     * enumerator class to define an order for the sorting function
     */
    enum class ORDER{
        ASC,
        DEC
    }

    /**
     * used to generate the DB name, (category.name DB_token category.imageURI)
     */
    companion object {
        /**
         * token used to separate the data into the db
         */
        const val DBtoken: String = "_"

        /**
         * @return Category build with the data contained into the DB name, the category generated
         * will have an empty expenses list
         */
        fun loadCategoryFromDB(name: String): Category {
            val results = name.split(DBtoken)
            return Category(results[0], results[1])
        }
    }
}