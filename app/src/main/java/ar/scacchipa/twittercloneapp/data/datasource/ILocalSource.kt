package ar.scacchipa.twittercloneapp.data.datasource

interface ILocalSource {
    fun save(key: String, value: String)
    fun get(key: String): String?
    fun contains(key: String): Boolean
}