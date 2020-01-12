package com.spacex.repository.sources

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = sharedPreferences.getBoolean(FIRST_LAUNCH, true)
        set(flag) {
            sharedPreferences.edit().putBoolean(FIRST_LAUNCH, flag).apply()
        }

    companion object {
        private const val FIRST_LAUNCH = "_.FIRST_LAUNCH"
    }

}
