package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.datasource.ILocalSource
import ar.scacchipa.twittercloneapp.data.datasource.ILoggedUserExternalSource
import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_ID
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_NAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_PROFILE_IMAGE_URL
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_USERNAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_VERIFIED

class LoggedUserRepository(
    private val loggedUserLocalSource: ILocalSource,
    private val loggedUserExternalSource: ILoggedUserExternalSource
): ILoggedUserRepository {
    override suspend fun refreshLoggedUser(accessToken: String): Boolean {
        val userResponse = loggedUserExternalSource.getLoggedUserData(
            bearerCode = "Bearer $accessToken"
        )
        if (userResponse.isSuccessful) {
            userResponse.body()?.data?.let { userData ->
                storeLoggedUserData(userData)
            }
            return true
        }
        return false
    }

    override suspend fun getLoggedUser(): UserData? {
        return getLoggedUserData()
    }

    private fun storeLoggedUserData(userData: UserData) {
        loggedUserLocalSource.apply {
            save(LOGGED_USER_DATA_ID, userData.id)
            save(LOGGED_USER_DATA_VERIFIED, userData.verified)
            save(LOGGED_USER_DATA_NAME, userData.name)
            save(LOGGED_USER_DATA_USERNAME, userData.username)
            save(LOGGED_USER_DATA_PROFILE_IMAGE_URL, userData.profileImageUrl)
        }
    }

    private fun getLoggedUserData(): UserData? {
        loggedUserLocalSource.apply {
            return if (contains(LOGGED_USER_DATA_ID))
                UserData(
                    id = getString(LOGGED_USER_DATA_ID) ?: "",
                    verified = getBoolean(LOGGED_USER_DATA_VERIFIED),
                    name = getString(LOGGED_USER_DATA_NAME) ?: "",
                    username =  getString(LOGGED_USER_DATA_USERNAME) ?: "",
                    profileImageUrl =getString(LOGGED_USER_DATA_PROFILE_IMAGE_URL) ?: ""
                )
            else null
        }
    }
}