package gestionemoney.compose.controller

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import java.time.Duration
import java.time.Instant

class WriteLog {
    private var userID: String? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics //reference for the firebase object

    /**
     * used to implements the singleton pattern for the class
     */
    companion object {
        private var instance: WriteLog? = null

        fun getInstance(): WriteLog {
            return instance ?: synchronized(this) {
                instance ?: WriteLog().also { instance = it }
            }
        }
    }

    /**
     * set the user for the current log section
     */
    fun start(uid: String) {
        userID = uid
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.setUserId(uid)
    }

    /**
     * specific function to write time obtained by the function
     * of the Timer class
     */
    fun writeTime(eventType: String, time: Double) {
        if (!isConnect()) {return}
        Log.w("log", "$eventType $time")
        writeValue(eventType, time, "ms")
    }

    /**
     * generic function to write error message passed by the parameter
     */
    fun writeError(eventType: String, error: String) {
        if (!isConnect()) {return}
        Log.w("Logs", "$eventType $error")
        firebaseAnalytics.logEvent(eventType) {
            param("error-message", error)
        }
    }
    /**
     * generic function to write any value, it needs also the measurement unit
     */
    fun writeValue(eventType: String, value: Double, measureUnit: String) {
        if (!isConnect()) {return}
        Log.w("Logs", "$eventType $value $measureUnit")
        firebaseAnalytics.logEvent(eventType) {
            param(FirebaseAnalytics.Param.VALUE, value)
            param("measure-unit", measureUnit)
            param("userID", userID!!)
        }
    }

    /**
     * generic function to write simple log without a content
     */
    fun writeBasicLog(eventType: String) {
        if (!isConnect()) {return}
        Log.w("Logs", eventType)
        firebaseAnalytics.logEvent(eventType) {
            param("userID", userID!!)
        }
    }

    /**
     * generic function to write a simple logs containing the msg
     */
    fun writeMessage(eventType: String, msg: String) {
        if (!isConnect()) {return}
        firebaseAnalytics.logEvent(eventType) {
            param("message", msg)
            param("userID", userID!!)
        }
    }

    /**
     * @return true if the object is initialized properly using the method start
     */
    fun isConnect(): Boolean {
        return userID != null
    }
}

/**
 * class used to implements a simple timer
 * used as utilities for logs
 */
class Timer() {
    private var timer: Instant? = null

    /**
     * the function will start the counter for the timer
     */
    fun startTimer() {
        timer = Instant.now()
    }

    /**
     * it will stop the timer and return the mesured time
     */
    fun endTimer(): Double {
        return if(timer == null) {
            0.0
        } else {
            Duration.between(timer, Instant.now()).toMillis().toDouble()
        }
    }
}