package gestionemoney.compose.controller


import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import gestionemoney.compose.model.Category
import gestionemoney.compose.model.Expense
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
        private const val dbNode = "userData"
        fun getInstance(): DBUserConnection {
            return instance ?: synchronized(this) {
                instance ?: DBUserConnection().also { instance = it }
            }
        }
    }
    fun connectUser(uid: String) {
        userID = uid
        val myRef = database.getReference(dbNode).child(uid)
        Log.w("db", "connecting")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("db", "readed")
                readUserData(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                notifyError(error.message)
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

    private fun notifyError(error: String) {
        userObservers.forEach { it.updateError(error) }
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
        val myRef = database.getReference(dbNode)
        Log.w("write", user!!.toHashmap().toString())
        myRef.child(userID!!).updateChildren(user!!.toHashmap())
        user!!.getList().forEach { cat ->
            myRef.child(userID!!).child(cat.getDBname()).updateChildren(cat.toHashmap()).addOnFailureListener {
                notifyError(it.message.toString())
            }
            Log.w("write", cat.toHashmap().toString())
        }
    }

    fun writeCategoryName(categoryName: String) {
        if (!isConnect()) {
            return
        }
        if (user!!.getCategory(categoryName) == null) {
            return
        }
        val category: Category = user!!.getCategory(categoryName)!!
        val map: HashMap<String, Any> = HashMap()
        map[category.getDBname()] = ""
        database.getReference(dbNode)
            .child(userID!!)
            .updateChildren(map).addOnSuccessListener {
                notifyUserObservers(user!!)
            }
            .addOnFailureListener {
                notifyError(it.message.toString())
            }
    }

    fun writeLastExpense(categoryName: String) {
        if (!isConnect()) {
            return
        }
        if (user!!.getCategory(categoryName) == null) {
            return
        }
        val category: Category = user!!.getCategory(categoryName)!!
        val myRef = database.getReference(dbNode)
            .child(userID!!)
            .child(category.getDBname())
        val lastExpense = category.lastExpenseHashMap() ?: return
        myRef.updateChildren(lastExpense).addOnSuccessListener {
            notifyUserObservers(user!!)
        }
            .addOnFailureListener {
                notifyError(it.message.toString())
            }
    }

    fun deleteCategory(categoryName: String) {
        if (!isConnect()) {
            return
        }
        if (user!!.getCategory(categoryName) == null) {
            return
        }
        val category: Category = user!!.getCategory(categoryName)!!
        val myRef = database.getReference(dbNode)
            .child(userID!!)
            .child(category.getDBname())
        myRef.removeValue().addOnSuccessListener {
            user!!.deleteCategory(categoryName)
            notifyUserObservers(user!!)
        }
            .addOnFailureListener {
                notifyError(it.message.toString())
            }
    }

    fun deleteExpense(categoryName: String, expenseDate: String) {
        if (!isConnect()) {
            return
        }
        if (user!!.getCategory(categoryName) == null) {
            return
        }
        if(user!!.getCategory(categoryName)!!.getExpense(expenseDate) == null) {
            return
        }
        val category: Category = user!!.getCategory(categoryName)!!
        val expense: Expense = category.getExpense(expenseDate)!!
        val myRef = database.getReference(dbNode)
            .child(userID!!)
            .child(category.getDBname())
            .child(expense.getDBName())
        myRef.removeValue().addOnSuccessListener {
            user!!.deleteCategory(categoryName)
            notifyUserObservers(user!!)
        }
            .addOnFailureListener {
                notifyError(it.message.toString())
            }
    }
    fun close() {
        instance = null
        user = null
        UserWrapper.getInstance().close()
    }
}