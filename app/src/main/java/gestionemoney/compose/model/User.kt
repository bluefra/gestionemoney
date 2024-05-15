package gestionemoney.compose.model

import kotlin.jvm.Throws

class User(private val userID: String) {
    private var categoriesList: MutableList<Category> = mutableListOf()

    @Throws(IllegalArgumentException::class)
    fun addCategory(category: Category){
        categoriesList.forEach {
            if(category.compareName(it) == 0) {
                throw IllegalArgumentException("already Existing category")
            }
        }
        categoriesList.add(category)
    }

    fun orderByName(order: Category.ORDER): List<Category> {
        return when (order) {
            Category.ORDER.ASC -> categoriesList.sortedWith { c0, c1 -> c0.compareName(c1) }
            Category.ORDER.DEC -> categoriesList.sortedWith { c0, c1 -> -c0.compareName(c1) }
        }
    }

    fun getList(): List<Category> {
        return categoriesList
    }

    fun getUID(): String {
        return userID
    }
    fun getCategory(name: String): Category? {
        categoriesList.forEach {
            if(it.compareName(name) == 0) {
                return it
            }
        }
        return null
    }

    fun toHashmap(): HashMap<String, Any>  {
        val map: HashMap<String, Any> = HashMap()
        categoriesList.forEach {
            map[it.getDBname()] = ""
        }
        return map
    }

    fun loadFromHashmap(map: HashMap<String, Any>) {
        map.forEach {
            val category = Category.loadCategoryFromDB(it.key)
            val expense: HashMap<String, Double>? = it.value as? HashMap<String, Double>
            if(expense != null) {
                category.loadFromHashmap(expense)
            }
            categoriesList.add(category)
        }
    }

    override fun toString(): String {
        var s = "$userID\n"
        categoriesList.forEach {
            s += it.toString()
        }
        return s
    }
}