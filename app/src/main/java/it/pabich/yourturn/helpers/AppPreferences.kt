package it.pabich.yourturn.helpers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class AppPreferences private constructor(context: Context) {

    private val sharedPreferences : SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE)
    }

    companion object : SingletonHolder<AppPreferences, Context>(::AppPreferences) {
        private const val EMPTY_STRING = ""

        private const val LAST_OPENED_GROUP = "lastOpenedGroup"

        private const val APP_SHARED_PREFS = "MyPrefs"
    }

    /**
     * Get or set the encrypted password for hidden settings
     */
    var lastOpenedGroup: String?
        get() = sharedPreferences.getString(LAST_OPENED_GROUP, EMPTY_STRING)
        set(password) = sharedPreferences.edit().putString(LAST_OPENED_GROUP, password).apply()



}