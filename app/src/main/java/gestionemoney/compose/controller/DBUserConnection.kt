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
import gestionemoney.compose.model.User


/**
 * class used to connect the application with the firebase db
 * <strong>warning</strong> this class user, and the UserWrapper user are the same object, and so
 * to maintain correct functioning of the software, the object in the UserWrapper should never change
 * @see UserWrapper
 */
class DBUserConnection private constructor() {
    private val database = Firebase.database
    private val userObservers = mutableListOf<UserChangeObserver>()
    private var user: User? = null
    private var userID: String? = null
    private var initialized: Boolean = false
    
    init {
        if(!initialized) {
            database.setPersistenceEnabled(true)
            initialized = true
        }
    }
    /**
     * used to implements the singleton pattern
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

    /**
     * it will try to read the data about the user passed by parameter, if the user isn't in the db,
     * it will create a new user.
     * on success/un-success it will notify the observer
     * @see UserChangeObserver
     */
    fun connectUser(uid: String) {
        userID = uid
        val timer = Timer()
        timer.startTimer()
        val myRef = database.getReference(dbNode).child(uid)
        Log.w("db", "connecting")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.w("db", "readed")
                readUserData(dataSnapshot)
                WriteLog.getInstance().writeTime("DBU_connection_succ", timer.endTimer()) //FIREBASE LOG
            }

            override fun onCancelled(error: DatabaseError) {
                notifyError(error.message)
                WriteLog.getInstance().writeTime("DBU_connection_err", timer.endTimer()) //FIREBASE LOG
                WriteLog.getInstance().writeError("DBU_connection_err_message", error.message) //FIREBASE LOG
            }
        })
    }

    /**
     * add a class to the observers list
     */
    fun addUserObserver(observer: UserChangeObserver) {
        userObservers.add(observer)
    }
    /**
     * remove a class to the observers list
     */
    fun removeUserObserver(observer: UserChangeObserver) {
        userObservers.remove(observer)
    }
    /**
     * notify the observer of the successful read, passing the user wrapped in the
     * UserWrapper
     * @see UserChangeObserver
     * @see UserWrapper
     * @param user -> data read from the db
     */
    private fun notifyUserObservers(user: User) {
        val userWrapper: UserWrapper = UserWrapper.getInstance()
        userWrapper.updateUser(user)
        Log.w("db", "notifyng")
        userObservers.forEach { it.updateUser(userWrapper) }
    }

    /**
     * notify the observer of the successful read, passing the user wrapped in the
     * UserObserver
     *
     * @see UserChangeObserver
     * @param error -> error message
     */
    private fun notifyError(error: String) {
        userObservers.forEach { it.updateError(error) }
    }

    /**
     * called after a successful db connection to elaborate the data and store them in a user
     * object
     */
    private fun readUserData(value: DataSnapshot) {
        val map: HashMap<String, Any>?
        user = User(userID!!)
        map = value.getValue<HashMap<String,Any>>()
        if(map != null) {
            user!!.loadFromHashmap(map)
        }
        notifyUserObservers(user!!)
    }

    /**
     * @return true if the object is properly set and a read from the db has been made
     */
    fun isConnect(): Boolean {
        return !(userID == null || user == null)
    }

    /**
     * write the entire user data onto the db, even if the user already exist
     */
    fun writeUser() {
        if(!isConnect()) { return }
        val myRef = database.getReference(dbNode)
        Log.w("write", user!!.toHashmap().toString())
        myRef.child(userID!!).updateChildren(user!!.toHashmap())
        user!!.getList().forEach { cat ->
            myRef.child(userID!!).child(cat.getDBname()).updateChildren(cat.toHashmap()).addOnFailureListener {
                notifyError(it.message.toString())
            }.addOnFailureListener {
                notifyError(it.message.toString())
            }
            Log.w("write", cat.toHashmap().toString())
        }
    }

    /**
     * write a single category name into the db.
     * <strong>warning</strong> this function should always be preferred over writeUser() if
     * possible, to reduce the load on the db side
     */
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
    /**
     * write the last expense associated with the category name passed onto the db.
     * <strong>warning</strong> this function should always be preferred over writeUser() if
     * possible, to reduce the load on the db side
     */
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
        Log.w("adding", lastExpense.toString())
        myRef.updateChildren(lastExpense).addOnSuccessListener {
            notifyUserObservers(user!!)
        }
            .addOnFailureListener {
                Log.w("adding error", it.message.toString())
                notifyError(it.message.toString())
            }
    }

    /**
     * delete an entire category, with all of the expense from the db.
     */
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
            WriteLog.getInstance().writeBasicLog("DBU_expense_deleted")
            notifyUserObservers(user!!)
        }
            .addOnFailureListener {
                notifyError(it.message.toString())
                WriteLog.getInstance().writeError("DBU_expense_deleted_error", it.message.toString())
            }
    }

    /**
     * delete an expense, associated with a specific category name from the db.
     */
    fun deleteExpense(categoryName: String, expenseDate: String) {
        Log.w("delete expense", expenseDate)
        if (!isConnect()) {
            return
        }
        val category: Category = user!!.getCategory(categoryName) ?: return
        val expense: Expense = category.getExpenseByString(expenseDate) ?: return
        user!!.getCategory(categoryName)!!.deleteExpense(expense)
        val myRef = database.getReference(dbNode)
            .child(userID!!)
            .child(category.getDBname())
            .child(expense.getDBName())
        myRef.removeValue().addOnSuccessListener {
            notifyUserObservers(user!!)
            if(category.getList().isEmpty()) {
                writeCategoryName(categoryName)
            }
        }
            .addOnFailureListener {
                notifyError(it.message.toString())
            }
    }

    /**
     * close the singleton instance and delete all the data saved (locally).
     */
    fun close() {
        user = null
        userID = null
        UserWrapper.getInstance().close()
    }
}