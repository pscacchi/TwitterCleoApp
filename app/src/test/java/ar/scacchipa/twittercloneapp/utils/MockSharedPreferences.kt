package ar.scacchipa.twittercloneapp.utils

import android.content.SharedPreferences

class MockSharedPreferences: SharedPreferences {

    private val mutableMap = mutableMapOf<String, Any?>()
    private var committed = true

    override fun getAll(): MutableMap<String, *> {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getString(key: String?, defValue: String?): String? {
        return if (committed) {
            mutableMap[key] as String?
        } else {
            null
        }
    }
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return if (committed) {
            when (key) {
                Constants.EXPIRES_IN -> MockTokenProvider.userAccessTokenDomain1().expiresIn
                else -> defValue
            }
        } else {
            defValue
        }
    }

    override fun getLong(key: String?, defValue: Long): Long {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        throw NotImplementedError("Not yet implemented")
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        throw NotImplementedError("Not yet implemented")
    }

    override fun contains(key: String?): Boolean {
        return if (committed) {
            mutableMap.contains(key)
        } else {
            false
        }
    }

    override fun edit(): SharedPreferences.Editor {
        committed = false
        return MockSharedPreferencesEditor()
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        throw NotImplementedError("Not yet implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        throw NotImplementedError("Not yet implemented")
    }

    inner class MockSharedPreferencesEditor: SharedPreferences.Editor {
        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            if (key != null) {
                mutableMap[key] = value
            } else {
                throw NullPointerException()
            }
            return this
        }

        override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
            throw NotImplementedError("Not yet implemented")
        }

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
            if (key != null) {
                mutableMap[key] = value
            } else {
                throw NullPointerException()
            }
            return this
        }

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
            throw NotImplementedError("Not yet implemented")
        }

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
            throw NotImplementedError("Not yet implemented")
        }

        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
            throw NotImplementedError("Not yet implemented")
        }

        override fun remove(key: String?): SharedPreferences.Editor {
            mutableMap.remove(key)
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            mutableMap.clear()
            return this
        }

        override fun commit(): Boolean {
            committed = true
            return true
        }

        override fun apply() {
            throw NotImplementedError("Not yet implemented")
        }
    }
}