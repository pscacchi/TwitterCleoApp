package ar.scacchipa.twittercloneapp.data.datasource

class MockLocalStorage: ILocalSource {
    private val values = mutableMapOf<String, String>()

    override fun save(key: String, value: String) {
        values[key] = value
    }

    override fun get(key: String): String? {
        return values[key]
    }

    override fun contains(key: String): Boolean {
        return values.contains(key)
    }

    override fun remove(key: String) {
        values.remove(key)
    }
}