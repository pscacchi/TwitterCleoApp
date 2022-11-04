package ar.scacchipa.twittercloneapp.data.datasource

class MockLocalStorage: ILocalSource {
    private val stringValues = mutableMapOf<String, String>()
    private val booleanValues = mutableMapOf<String, Boolean>()

    override fun save(key: String, value: String) {
        stringValues[key] = value
    }

    override fun save(key: String, value: Boolean) {
        booleanValues[key] = value
    }

    override fun getString(key: String): String? {
        return stringValues[key]
    }

    override fun getBoolean(key: String): Boolean {
        return booleanValues[key] ?: throw Error()
    }

    override fun contains(key: String): Boolean {
        return stringValues.contains(key)
    }

    override fun remove(key: String) {
        stringValues.remove(key)
    }
}