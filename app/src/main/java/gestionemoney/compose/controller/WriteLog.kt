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

    private fun write(eventType: String, key: String, message: String) {
        if (!isConnect()) {return}
        firebaseAnalytics.logEvent(eventType) {
            param(key, message)
        }
    }

    fun writeTime(eventType: String, time: Long) {
        if (!isConnect()) {return}
        val formattedNumber = String.format("%.2f", time.toDouble())
        Log.w("log", "$eventType $formattedNumber")
        firebaseAnalytics.logEvent(eventType) {
            param(FirebaseAnalytics.Param.VALUE, formattedNumber)
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

    fun endTimer(): Long {
        return if(timer == null) {
            0
        } else {
            Duration.between(timer, Instant.now()).toMillis()
        }
    }
}