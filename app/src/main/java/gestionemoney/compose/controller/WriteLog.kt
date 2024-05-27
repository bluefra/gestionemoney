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
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    companion object {
        private var instance: WriteLog? = null

        fun getInstance(): WriteLog {
            return instance ?: synchronized(this) {
                instance ?: WriteLog().also { instance = it }
            }
        }
    }

    fun start(uid: String) {
        userID = uid
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.setUserId(uid)
    }

    fun writeTime(eventType: String, time: Double) {
        if (!isConnect()) {return}
        Log.w("log", "$eventType $time")
        writeValue(eventType, time, "ms")
    }

    fun writeError(eventType: String, error: String) {
        if (!isConnect()) {return}
        firebaseAnalytics.logEvent(eventType) {
            param("error-message", error)
        }
    }

    fun writeValue(eventType: String, value: Double, measureUnit: String) {
        if (!isConnect()) {return}
        firebaseAnalytics.logEvent(eventType) {
            param(FirebaseAnalytics.Param.VALUE, value)
            param("measure-unit", measureUnit)
            param("userID", userID!!)
        }
    }

    fun writeBasicLog(eventType: String) {
        if (!isConnect()) {return}
        firebaseAnalytics.logEvent(eventType) {
            param("userID", userID!!)
        }
    }

    fun isConnect(): Boolean {
        return userID != null
    }
}

class Timer() {
    private var timer: Instant? = null
    fun startTimer() {
        timer = Instant.now()
    }

    fun endTimer(): Double {
        return if(timer == null) {
            0.0
        } else {
            Duration.between(timer, Instant.now()).toMillis().toDouble()
        }
    }
}