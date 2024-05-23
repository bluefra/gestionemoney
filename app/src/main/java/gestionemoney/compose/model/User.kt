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

    fun orderByValue(order: Category.ORDER): List<Category> {
        return when (order) {
            Category.ORDER.ASC -> categoriesList.sortedWith { c0, c1 ->
                val c0Total = c0.GetTotalExpences()
                val c1Total = c1.GetTotalExpences()
                if (c0Total == c1Total) {
                    return@sortedWith 0
                } else if(c0Total > c1Total) {
                    return@sortedWith 1
                }
                return@sortedWith -1
            }
            Category.ORDER.DEC -> categoriesList.sortedWith { c0, c1 ->
                val c0Total = c0.GetTotalExpences()
                val c1Total = c1.GetTotalExpences()
                if (c0Total == c1Total) {
                    return@sortedWith 0
                } else if(c0Total > c1Total) {
                    return@sortedWith -1
                }
                return@sortedWith 11
            }
        }
    }

    fun deleteCategory(categoryName: String) {
        categoriesList.remove(getCategory(categoryName))
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