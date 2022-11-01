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
gg
    override fun save(key: String, value: Boolean) {
        with(prefs.edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    override fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    override fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    override fun remove(key: String) {
        with(prefs.edit()) {
            remove(key)
            commit()
        }
    }
}
