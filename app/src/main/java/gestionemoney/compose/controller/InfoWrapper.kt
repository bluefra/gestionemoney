package gestionemoney.compose.controller

import gestionemoney.compose.model.Info

class InfoWrapper private constructor(){
    private var info: Info? = null
    /**
     * usato implementare il pattern Singleton nella classe UserWrapper
     */
    companion object {
        private var instance: InfoWrapper? = null

        fun getInstance(): InfoWrapper {
            return instance ?: synchronized(this) {
                instance ?: InfoWrapper().also { instance = it }
            }
        }
    }

    fun updateInfo(inf: Info) {
        info = inf
    }
    fun setInfo(key: String, content:String) {
        info?.setInfo(key, content)
    }

    fun getInfo(key: String): String {
        return info?.getInfo(key) ?: ""
    }

    fun getHashMap(): HashMap<String, Any> {
        return if(info == null) {
           HashMap()
        } else {
            info!!.getHashMap()
        }
    }

    fun setHasMap(value: HashMap<String, Any>) {
        info?.setHashMap(value)
    }

    fun close() {
        instance = null
        info = null
    }
}