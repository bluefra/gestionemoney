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

        fun getInstance(): DBInfoConnection {
            return instance ?: synchronized(this) {
                instance ?: DBInfoConnection().also { instance = it }
            }
        }
    }

    fun connectInfo(uid: String) {
        userID = uid
        val myRef = database.getReference("userInfo").child(uid)
        Log.w("info", "connecting")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("info", "readed")
                readInfoData(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun readInfoData(value: DataSnapshot) {
        val map: HashMap<String, Any>?
        map = value.getValue<HashMap<String,Any>>()
        if(map != null) {
            info.setHasMap(map)
        }
        notifyInfoObservers(info)
    }

    fun writeInfo() {
        if(!isConnect()) { return }
        val myRef = database.getReference("userInfo")
            myRef.child(userID!!).updateChildren(info.getHashMap())
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
}