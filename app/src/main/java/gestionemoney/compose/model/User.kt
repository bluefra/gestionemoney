package gestionemoney.compose.model

import kotlin.jvm.Throws

class User(private val userID: String) {
    /**
     * list of all the categories of an user
     */
    private var categoriesList: MutableList<Category> = mutableListOf()

    /**
     * add the category passed to the list
     * @throws IllegalArgumentException if the category already exists
     */
    @Throws(IllegalArgumentException::class)
    fun addCategory(category: Category){
        categoriesList.forEach {
            if(category.compareName(it) == 0) {
                throw IllegalArgumentException("already Existing category")
            }
        }
        categoriesList.add(category)
    }

    /**
     * @return List<Category> of the user ordered by name by ASC, DEC
     */
    fun orderByName(order: Category.ORDER): List<Category> {
        return when (order) {
            Category.ORDER.ASC -> categoriesList.sortedWith { c0, c1 -> c0.compareName(c1) }
            Category.ORDER.DEC -> categoriesList.sortedWith { c0, c1 -> -c0.compareName(c1) }
        }
    }

    /**
     * @return List<Category> of the user ordered by value by ASC, DEC
     * -> the value of a category is the sum of all the expense value associated with that category
     */
    fun orderByValue(order: Category.ORDER): List<Category> {
        return when (order) {
            Category.ORDER.ASC -> categoriesList.sortedWith { c0, c1 ->
                val c0Total = c0.getTotalExpenses()
                val c1Total = c1.getTotalExpenses()
                if (c0Total == c1Total) {
                    return@sortedWith 0
                } else if(c0Total > c1Total) {
                    return@sortedWith 1
                }
                return@sortedWith -1
            }
            Category.ORDER.DEC -> categoriesList.sortedWith { c0, c1 ->
                val c0Total = c0.getTotalExpenses()
                val c1Total = c1.getTotalExpenses()
                if (c0Total == c1Total) {
                    return@sortedWith 0
                } else if(c0Total > c1Total) {
                    return@sortedWith -1
                }
                return@sortedWith 11
            }
        }
    }

    /**
     * remove the category associated with the name passed form the cateogries list
     */
    fun deleteCategory(categoryName: String) {
        categoriesList.remove(getCategory(categoryName))
    }

    /**
     * @return the category list as it is saved
     */
    fun getList(): List<Category> {
        return categoriesList
    }

    /**
     * @return the userID
     */
    fun getUID(): String {
        return userID
    }

    /**
     * @return the category with the name passed as parameter, null if a category
     */
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
    @Suppress("UNCHECKED_CAST")
    //i know th type, the casting is necessary cause the firebase standard
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