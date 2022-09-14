package ar.scacchipa.twittercloneapp.data.datasource

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SharedPrefsLocalStorageTest {

    private val mockSharedEditor = mockk<SharedPreferences.Editor>()
    private val mockSharedPreferences = mockk<SharedPreferences>()

    private val subject = SharedPrefsLocalStorage(
        prefs = mockSharedPreferences
    )

    @Before
    fun setup() {



    }

    @Test
    fun subjectSaveKeyAndValue() {

        every {
            mockSharedPreferences.edit()
        } returns mockSharedEditor

        every {
            mockSharedEditor.putString( any(), any() )
        } answers {
            this.callOriginal()
        }

        every {
            mockSharedEditor.commit()
        } returns true

        subject.save("aKey", "aValue")

        verify {
            mockSharedPreferences.edit()
            mockSharedEditor.putString("aKey", "aValue")
            mockSharedEditor.commit()
        }
    }

    @Test
    fun subjectGetAString() {
        every {
            mockSharedPreferences.getString("aKey1", null)
        } returns "aString1"

        subject.get("aKey1")
        verify {
            mockSharedPreferences.getString("aKey1", null)
        }
    }

    @Test
    fun subjectContainsReturnTrue() {
        every {
            mockSharedPreferences.contains("aKey3")
        } returns true

        subject.contains("aKey3")
        verify {
            mockSharedPreferences.contains("aKey3")
        }
    }
}

class SharedPrefsLocalStoragex(
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