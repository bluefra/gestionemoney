package gestionemoney.compose.controller

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import gestionemoney.compose.model.Info

/**
 * class used to connect the application with the firebase db
 * <strong>warning</strong> this class info, and the InfoWrapper info are the same object, and so
 * to maintain the correct functioning of the software, the object in the InfoWrapper should never change
 * @see InfoWrapper
 */
class DBInfoConnection {
    private val database = Firebase.database
    private var info: Info = Info()
    private val infoObservers = mutableListOf<InfoChangeObserver>()
    private var userID: String? = null
    private var initiazied: Boolean = false
    init {
        if(!initiazied) {
            database.setPersistenceEnabled(true)
            initiazied = true
        }
    }

    /**
     * used to implement the singleton pattern
     */
    companion object {
        private var instance: DBInfoConnection? = null
        private const val dbNode = "userInfo"
        fun getInstance(): DBInfoConnection {
            return instance ?: synchronized(this) {
                instance ?: DBInfoConnection().also { instance = it }
            }
        }
    }
    /**
     * it will try to read the data about the info associated with the user passed by parameter,
     * if the data isn't in the db, it will create a new set of info.
     * on success it will notify the observer
     * @see InfoChangeObserver
     */
    fun connectInfo(uid: String) {
        userID = uid
        val timer = Timer()
        timer.startTimer()
        val myRef = database.getReference(dbNode).child(uid)
        Log.w("info", "connecting")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("info", "readed")
                readInfoData(dataSnapshot)
                WriteLog.getInstance().writeTime("DBI_connection_succ", timer.endTimer()) //FIREBASE LOG
            }

            override fun onCancelled(error: DatabaseError) {
                WriteLog.getInstance().writeTime("DBI_connection_err", timer.endTimer()) //FIREBASE LOG
                WriteLog.getInstance().writeError("DBI_connection_err_message", error.message) //FIREBASE LOG
            }

        })
    }

    /**
     * called by the function connectInfo in case of success, and it will store the data and notify
     * the observers.
     * @see InfoChangeObserver
     * @see connectInfo
     */
    fun readInfoData(value: DataSnapshot) {
        val map: HashMap<String, Any>?
        map = value.getValue<HashMap<String,Any>>()
        if(map != null) {
            info.setHashMap(map)
        }
        notifyInfoObservers(info)
    }

    /**
     * write all the info into the db.
     * <strong>warning</strong> this method will override all of the info data, so it's
     * discouraged the usage if the majority of the data are already loaded into the db.
     */
    fun writeInfo() {
        if(!isConnect()) { return }
        val myRef = database.getReference(dbNode)
        myRef.child(userID!!).updateChildren(info.getHashMap())
    }

    /**
     * create a new info with the passed parameter and write it into the db
     * <strong>it's suggest to use this one to write data onto the db</strong>
     */
    fun writeSingleInfo(key: String, value: String) {
        if(!isConnect()) { return }
        val map: HashMap<String, Any> = HashMap()
        val myRef = database.getReference(dbNode)
        map[key] = value
        info.setInfo(key, value)
        myRef.child(userID!!).updateChildren(map)
    }

    /**
     * a single entry from the db associated with the key passed as a parameter.
     */
    fun removeSingleInfo(key: String) {
        if(!isConnect()) { return }
        val myRef = database.getReference(dbNode)
        if(info.removeInfo(key)) {
            myRef.child(userID!!).child(key).removeValue()
        }
    }

    /**
     * write all of the info contained into the hashmap onto the db, if an info already exist it
     * will override it.
     * <strong>it's the best method to write data onto the db</strong>
     */
    //the cast is suppressed because, it's a mandatory cast caused by the firebase standard
    //and so i'm sure of typing
    @Suppress("UNCHECKED_CAST")
    fun writeMultipleInfo(map: HashMap<String, String>) {
        val myRef = database.getReference(dbNode)
        info.setHashMap(map as HashMap<String, Any>)
        myRef.child(userID!!).updateChildren(map as HashMap<String, Any>)
    }

    /**
     * return true if the class has been initialized correctly
     */
    fun isConnect(): Boolean {
        return userID != null
    }

    /**
     * add an object to the observer list
     * @see InfoChangeObserver
     */
    fun addInfoObserver(observer: InfoChangeObserver) {
        infoObservers.add(observer)
    }

    /**
     * remove an object to the observer list
     * @see InfoChangeObserver
     */
    fun removeInfoObserver(observer: InfoChangeObserver) {
        infoObservers.remove(observer)
    }
    /**
     * notify all of the observer when the info data are successfully read from the db
     * it will pass as parameter the info data wrapped into the InfoWrapper.
     * @see InfoChangeObserver
     * @see InfoWrapper
     */
    private fun notifyInfoObservers(info: Info) {
        InfoWrapper.getInstance().updateInfo(info)
        infoObservers.forEach { it.updateInfo(InfoWrapper.getInstance()) }
    }

    /**
     * delete the instance of hte class and all of the data associated
     */
    fun close() {
        info = Info()
        userID = null
        InfoWrapper.getInstance().close()
    }
}