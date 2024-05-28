package gestionemoney.compose.controller

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import gestionemoney.compose.model.Info

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

    companion object {
        private var instance: DBInfoConnection? = null
        private const val dbNode = "userInfo"
        fun getInstance(): DBInfoConnection {
            return instance ?: synchronized(this) {
                instance ?: DBInfoConnection().also { instance = it }
            }
        }
    }

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

    fun readInfoData(value: DataSnapshot) {
        val map: HashMap<String, Any>?
        map = value.getValue<HashMap<String,Any>>()
        if(map != null) {
            info.setHashMap(map)
        }
        notifyInfoObservers(info)
    }

    fun writeInfo() {
        if(!isConnect()) { return }
        val myRef = database.getReference(dbNode)
        myRef.child(userID!!).updateChildren(info.getHashMap())
    }

    fun writeSingleInfo(key: String, value: String) {
        if(!isConnect()) { return }
        val map: HashMap<String, Any> = HashMap()
        val myRef = database.getReference(dbNode)
        map[key] = value
        info.setInfo(key, value)
        myRef.child(userID!!).updateChildren(map)
    }

    fun removeSingleInfo(key: String) {
        if(!isConnect()) { return }
        val myRef = database.getReference(dbNode)
        if(info.removeInfo(key)) {
            myRef.child(userID!!).child(key).removeValue()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun writeMultipleInfo(map: HashMap<String, String>) {
        val myRef = database.getReference(dbNode)
        info.setHashMap(map as HashMap<String, Any>)
        myRef.child(userID!!).updateChildren(map as HashMap<String, Any>)
    }
    fun isConnect(): Boolean {
        return userID != null
    }

    fun addInfoObserver(observer: InfoChangeObserver) {
        infoObservers.add(observer)
    }

    fun removeInfoObserver(observer: InfoChangeObserver) {
        infoObservers.remove(observer)
    }

    private fun notifyInfoObservers(info: Info) {
        InfoWrapper.getInstance().updateInfo(info)
        infoObservers.forEach { it.updateInfo(InfoWrapper.getInstance()) }
    }

    fun close() {
        info = Info()
        userID = null
        InfoWrapper.getInstance().close()
    }
}