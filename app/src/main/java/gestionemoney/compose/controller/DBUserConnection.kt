package gestionemoney.compose.controller


import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import gestionemoney.compose.model.Info
import gestionemoney.compose.model.User


/**
 * per utilizzare la seguente classe è necessario invocare: DBconnection.getInstance()
 * non è possibile creare direttamente la classe
 */
class DBUserConnection private constructor() {
    private val database = Firebase.database
    private val userObservers = mutableListOf<UserChangeObserver>()
    private var user: User? = null
    private var userID: String? = null

    /**
     * usato implementare il pattern Singleton nella classe DBconnection
     */
    companion object {
        private var instance: DBUserConnection? = null

        fun getInstance(): DBUserConnection {
            return instance ?: synchronized(this) {
                instance ?: DBUserConnection().also { instance = it }
            }
        }
    }

    fun connectUser(uid: String) {
        userID = uid
        val myRef = database.getReference("userData").child(uid)
        Log.w("db", "connecting")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("db", "readed")
                readUserData(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun addUserObserver(observer: UserChangeObserver) {
        userObservers.add(observer)
    }

    fun removeUserObserver(observer: UserChangeObserver) {
        userObservers.remove(observer)
    }

    private fun notifyUserObservers(user: User) {
        val userWrapper: UserWrapper = UserWrapper.getInstance()
        userWrapper.updateUser(user)
        Log.w("db", "notifyng")
        userObservers.forEach { it.updateUser(userWrapper) }
    }

    private fun readUserData(value: DataSnapshot) {
        val map: HashMap<String, Any>?
        user = User(userID!!)
        map = value.getValue<HashMap<String,Any>>()
        if(map != null) {
            user!!.loadFromHashmap(map)
        }
        notifyUserObservers(user!!)
    }

    fun isConnect(): Boolean {
        return !(userID == null || user == null)
    }
    fun writeUser() {
        if(!isConnect()) { return }
        val myRef = database.getReference("userData")
        val categoryList = user!!.getList()
        val category = HashMap<String, Any>()
        categoryList.forEach {
            category[it.getName()] = ""
        }
        myRef.child(userID!!).updateChildren(category)
        categoryList.forEach { cat ->
            val expense = HashMap<String, Any>()
            cat.getList().forEach {
                expense[it.getDate().toString()] = it.getValue()
            }
            myRef.child(userID!!).child(cat.getName()).updateChildren(expense)
        }
    }

}