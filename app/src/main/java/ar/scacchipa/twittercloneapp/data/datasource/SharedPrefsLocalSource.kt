package ar.scacchipa.twittercloneapp.data.datasource

import android.content.SharedPreferences

class SharedPrefsLocalSource(
    private val prefs: SharedPreferences
): ILocalSource {
    override fun save(key: String, value: String) {
        with(prefs.edit()) {
            putString(key, value)
            commit()
        }
    }

    override fun get(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun contains(key: String): Boolean {
        return prefs.contains(key)
    }
}