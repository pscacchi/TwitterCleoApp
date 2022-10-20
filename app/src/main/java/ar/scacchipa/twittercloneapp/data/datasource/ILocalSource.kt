package ar.scacchipa.twittercloneapp.data.datasource

interface ILocalSource {
    fun save(key: String, value: String)
    fun save(key: String, value: Boolean)
    fun getString(key: String): String?
    fun getBoolean(key: String): Boolean
    fun contains(key: String): Boolean
    fun remove(key: String)
}

