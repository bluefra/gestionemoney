package gestionemoney.compose.model

class Info {
    private val info = HashMap<String, String>()

    fun setInfo(key: String, content:String) {
        info[key] = content
    }

    fun getInfo(key: String): String {
        return if(info.contains(key)) {
            info[key]!!
        } else {
            ""
        }
    }

    fun getHashMap(): HashMap<String, Any> {
        return info as HashMap<String, Any>
    }

    fun setHashMap(value: HashMap<String, Any>) {
        val map = value as HashMap<String, String>
        map.forEach{
            info[it.key] = it.value
        }
    }
}