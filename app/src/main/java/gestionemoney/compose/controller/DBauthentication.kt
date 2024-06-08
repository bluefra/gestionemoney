package gestionemoney.compose.controller

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

/**
 * class that allows the connection with the authentication system offered by firebase
 */
class DBauthentication {
    private var auth = Firebase.auth
    private val observers = mutableListOf<AuthObserver>()
    private val data: HashMap<String, String?> = HashMap()
    private var isSet: Boolean = false

    /**
     * used to implement the singleton pattern
     */
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

    /**
     * @return the userId if it has been set correctly, otherwise return null
     */
    fun getUID(): String? {
        if(!isSet) {
            return null
        }
        return data["uid"]
    }
    /**
     * @return the email if it has been set correctly, otherwise return null
     */
    fun getEmail(): String? {
        if(!isSet) {
            return null
        }
        return data["email"]
    }

    /**
     * add an object to the list of observer
     * @see AuthObserver
     */
    fun addObserver(observer: AuthObserver) {
        observers.add(observer)
    }
    /**
     * remove an object to the list of observer
     * @see AuthObserver
     */
    fun removeObserver(observer: AuthObserver) {
        observers.remove(observer)
    }
    /**
     * notify all of the observer if the connection with the authentication system has been
     * successfully establish.
     * it will also pass an hashmap that contains all of the user data
     * @see AuthObserver
     */
    private fun notifySuccessObservers(data: HashMap<String, String?>) {
        observers.forEach { it.onSuccess(data) }
    }
    /**
     * notify all of the observer if the connection with the authentication system has been
     * unsuccessful.
     * it will also pass as the error message
     * @see AuthObserver
     */
    private fun notifyFailObservers(error: String) {
        observers.forEach { it.onFail(error) }
    }

    /**
     * verify if there is an user saved into the authentication system with the passed email and psw
     * -> if there is it will call saveData
     * -> if there isn't it will notify the observer about the error
     * @see saveData
     */
    fun login(email: String, password: String) {
        val timer = Timer()
        timer.startTimer()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveData()
                    WriteLog.getInstance().writeTime("DBA_login_succ", timer.endTimer())
                } else {
                    notifyFailObservers(task.exception.toString())
                    WriteLog.getInstance().writeTime("DBA_login_error", timer.endTimer())
                    WriteLog.getInstance().writeError("DBA_login_message", task.exception.toString())
                }
            }
    }

    /**
     * store the data fetch during a connection or a register and notify the observer
     * @see notifySuccessObservers
     */
    private fun saveData() {
        val user = Firebase.auth.currentUser
        if(user != null) {
            Log.w("auth", user.displayName.toString())
            data["name"] = user.displayName
            data["email"] = user.email
            data["uid"] = user.uid
            isSet = true
            WriteLog.getInstance().start(user.uid)//FIREBASE LOG
            notifySuccessObservers(data)
        } else {
            notifyFailObservers("data persi")
            WriteLog.getInstance().writeError("DBA_missing_data", "user == null on saveData")
        }
    }
    
    /**
     * register an user saved into the authentication system with the passed email and psw
     * -> if the transaction succeed it will call saveData
     * -> if the transaction failed it will notify the observer about the error
     * @see saveData
     */
    fun register(email: String, password: String) {
        Log.w("auth", "starting register")
        val timer = Timer()
        timer.startTimer()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    Log.w("auth", "success")
                    saveData()
                    WriteLog.getInstance().writeTime("DBA_register_succ", timer.endTimer())
                } else {
                    notifyFailObservers(task.exception.toString())
                    WriteLog.getInstance().writeTime("DBA_register_erorr", timer.endTimer())
                    WriteLog.getInstance().writeError("DBA_register_error_message", task.exception.toString())
                }
            }
    }

    /**
     * @return the user associated with the session, if no user is set, it will return null
     */
    fun getUser(): FirebaseUser?{
        return auth.currentUser
    }

    /**
     * @return all the data stored by the class as an hashmap
     */
    fun getData(): HashMap<String, String?> {
       return data
    }

    /**
     *  close the instance and disconnect the firebase object
     */
    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        instance = null
        isSet = false
    }

    /**
     * return true if the class is properly set and if the data has been fetched correctly
     */
    fun isSet(): Boolean {
        return isSet
    }
}