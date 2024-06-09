package gestionemoney.compose.model

/**
 * object that contains all the extra info that are not stored in the other model
 */
class Info {
    private val info = HashMap<String, String>()

    /**
     * add an info to the info list
     */
    fun setInfo(key: String, content:String) {
        info[key] = content
    }

    /**
     * @return the string associated with the passed key
     * if the info doesn't exist it will return ""
     */
    fun getInfo(key: String): String {
        return if(info.contains(key)) {
            info[key]!!
        } else {
            ""
        }
    }

    /**
     * delete the info associated with the passed key
     * @return true if the info is eliminated, false if the info doesn't exist
     */
    fun removeInfo(key: String): Boolean {
        return if(info.contains(key)) {
            info.remove(key)
            true
        } else {
            false
        }
    }

    /**
     * return the hashmap of all the info saved, <String, Any> so that it will follow the firebase
     * standard
     */
    @Suppress("UNCHECKED_CAST")
    //i know th type, the casting is necessary cause the firebase standard
    fun getHashMap(): HashMap<String, Any> {
        return info as HashMap<String, Any>
    }

    /**
     * load the info from the hashmap passed as parameter
     */
    @Suppress("UNCHECKED_CAST")
    //i know th type, the casting is necessary cause the firebase standard
    fun setHashMap(value: HashMap<String, Any>) {
        val map = value as HashMap<String, String>
        map.forEach{
            info[it.key] = it.value
        }
    }
}