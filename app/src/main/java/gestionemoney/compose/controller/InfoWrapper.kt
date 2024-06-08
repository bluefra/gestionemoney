package gestionemoney.compose.controller

import gestionemoney.compose.model.Info

/**
 * class used to wrap an info object from the model, adding extra interface
 * @see Info
 */
class InfoWrapper private constructor(){
    private var info: Info? = null
    /**
     * used to implements the singleton pattern
     */
    companion object {
        private var instance: InfoWrapper? = null

        fun getInstance(): InfoWrapper {
            return instance ?: synchronized(this) {
                instance ?: InfoWrapper().also { instance = it }
            }
        }
    }

    /**
     * re-set the info object, the previous one will be lost if not saved
     */
    fun updateInfo(inf: Info) {
        info = inf
    }
    /**
     * create and add a single info
     */
    fun setInfo(key: String, content:String) {
        info?.setInfo(key, content)
    }

    /**
     * get a single info from the wrapped object matching the key value passed,
     * if there isn't an info with the passed key, it wil return ""
     */
    fun getInfo(key: String): String {
        return info?.getInfo(key) ?: ""
    }

    /**
     * @return an hashmap containing al the info saved as pair (key, content), [String, Any] so that
     * is already in the firebase standard to be push into the db
     */
    fun getHashMap(): HashMap<String, Any> {
        return if(info == null) {
           HashMap()
        } else {
            info!!.getHashMap()
        }
    }

    /**
     * load all the info from the hashmap passed, the parameter is uniform with the firebase
     * standard
     */
    fun setHasMap(value: HashMap<String, Any>) {
        info?.setHashMap(value)
    }

    /**
     * it will reset the class, losing all of the data saved if not copied somewhere else
     */
    fun close() {
        instance = null
        info = null
    }
}