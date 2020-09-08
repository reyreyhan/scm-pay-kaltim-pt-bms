package com.bm.main.scm.utils

import android.content.Context
import android.content.SharedPreferences
import com.bm.main.scm.SBFApplication

class AppSession {

    // Shared Preferences
    private lateinit var sharedPreferences: SharedPreferences

    companion object {

        const val PREF_SESSION = "PREF_SESSION_INFORMASI_WISATA"
        const val PREF_FIRST_TIME_LAUNCH = "PREF_FIRST_TIME_LAUNCH"
    }

    init {
//        sharedPreferences = MyApplication.applicationContext().getSharedPreferences(PREF_SESSION, Context.MODE_PRIVATE)
        sharedPreferences = SBFApplication.getInstance()!!.applicationContext.getSharedPreferences(
            PREF_SESSION,
            Context.MODE_PRIVATE
        )
    }


    fun clearSession() {
        // Clearing all data from Shared Preferences
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(PREF_FIRST_TIME_LAUNCH, isFirstTime)
        editor.apply()
    }

    fun setSharedPreferenceString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setSharedPreferenceInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun setSharedPreferenceBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setSharedPreferenceLong(key: String, value: Long?) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value!!)
        editor.apply()
    }

    fun setSharedPreferenceFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getSharedPreferenceString(key: String): String? {

        return sharedPreferences.getString(key, null)
    }

    fun getSharedPreferenceInt(key: String): Int {

        return sharedPreferences.getInt(key, 0)
    }

    fun getSharedPreferenceBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getSharedPreferenceLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun getSharedPreferenceFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun getToken(): String? {
        var token = getSharedPreferenceString(AppConstant.TOKEN)
        if (token != null) {
            if (token.isEmpty()) {
                token = ""
            }
        } else {
            token = ""
        }
        return token
    }

    fun getDeviceToken(): String? {
        var token = getSharedPreferenceString(AppConstant.DEVICE_TOKEN)
        if (token != null) {
            if (token.isEmpty()) {
                token = ""
            }
        } else {
            token = ""
        }
        return token
    }
}