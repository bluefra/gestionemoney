package gestionemoney.compose.controller

import android.content.Intent
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class DBauthentication {
    private var auth = Firebase.auth
    private val observers = mutableListOf<AuthObserver>()
    private val data: HashMap<String, String?> = HashMap()
    private var isSet: Boolean = false

    companion object {
        private var instance: DBauthentication? = null

        fun getInstance(): DBauthentication {
            return instance ?: synchronized(this) {
                instance ?: DBauthentication().also { instance = it }
            }
        }
    }
    init {
        if (getUser() != null) {
            saveData()
        }
    }
    fun getUID(): String? {
        if(!isSet) {
            return null
        }
        return data["uid"]
    }
    fun addObserver(observer: AuthObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: AuthObserver) {
        observers.remove(observer)
    }

    private fun notifySuccessObservers(data: HashMap<String, String?>) {
        observers.forEach { it.onSuccess(data) }
    }

    private fun notifyFailObservers(error: String) {
        observers.forEach { it.onFail(error) }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveData()
                } else {
                    notifyFailObservers(task.exception.toString())
                }
            }
    }
    private fun saveData() {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Log.w("auth", user.displayName.toString())
            data["name"] = user.displayName
            data["email"] = user.email
            data["uid"] = user.uid
            isSet = true
            notifySuccessObservers(data)
        } else {
            notifyFailObservers("data persi")
        }
    }
    fun register(email: String, password: String) {
        Log.w("auth", "starting register")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    Log.w("auth", "success")
                    saveData()
                } else {
                    notifyFailObservers(task.exception.toString())
                }
            }
    }
    fun getUser(): FirebaseUser?{
        return auth.currentUser
    }
    fun getData(): HashMap<String, String?> {
       return data
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        instance = null
        isSet = false
    }
    fun isSet(): Boolean {
        return isSet
    }
}