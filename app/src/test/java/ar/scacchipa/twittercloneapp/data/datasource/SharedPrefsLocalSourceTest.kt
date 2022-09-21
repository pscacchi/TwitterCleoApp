package ar.scacchipa.twittercloneapp.data.datasource

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SharedPrefsLocalSourceTest {

    private val mockSharedEditor = mockk<SharedPreferences.Editor>()
    private val mockSharedPreferences = mockk<SharedPreferences>()

    private val subject = SharedPrefsLocalSource(
        prefs = mockSharedPreferences
    )

    @Before
    fun setup() {
        every {
            mockSharedPreferences.edit()
        } returns mockSharedEditor

        every {
            mockSharedEditor.putString( any(), any() )
        } answers {
            this.callOriginal()
        }

        every {
            mockSharedEditor.remove( any() )
        } answers {
            this.callOriginal()
        }

        every {
            mockSharedEditor.commit()
        } returns true
    }

    @Test
    fun subjectSaveKeyAndValue() {

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

    @Test
    fun subjectRemoveAKey() {
        subject.remove("aKey4")

        verify {
            mockSharedPreferences.edit()
            mockSharedEditor.remove("aKey4")
            mockSharedEditor.commit()
        }
    }
}