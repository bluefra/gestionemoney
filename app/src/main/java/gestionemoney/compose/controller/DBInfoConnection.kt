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
    private val InfoObservers = mutableListOf<InfoChangeObserver>()
    private var userID: String? = null

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
                WriteLog.getInstance().writeTime("info_connection_succ", timer.endTimer()) //FIREBASE LOG
            }

            override fun onCancelled(error: DatabaseError) {
                WriteLog.getInstance().writeTime("info_connection_err", timer.endTimer()) //FIREBASE LOG
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
        InfoObservers.add(observer)
    }

    fun removeInfoObserver(observer: InfoChangeObserver) {
        InfoObservers.remove(observer)
    }

    private fun notifyInfoObservers(info: Info) {
        InfoWrapper.getInstance().updateInfo(info)
        InfoObservers.forEach { it.updateInfo(InfoWrapper.getInstance()) }
    }

    fun close() {
        instance = null
        info = Info()
        InfoWrapper.getInstance().close()
    }
}