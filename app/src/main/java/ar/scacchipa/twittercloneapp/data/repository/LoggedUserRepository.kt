package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.datasource.ILocalSource
import ar.scacchipa.twittercloneapp.data.datasource.ILoggedUserExternalSource
import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_ID
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_NAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_PROFILE_IMAGE_URL
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_USERNAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.LOGGED_USER_DATA_VERIFIED
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoggedUserRepository(
    private val loggedUserLocalSource: ILocalSource,
    private val loggedUserExternalSource: ILoggedUserExternalSource,
    private val credentialRepository: ICredentialRepository,
    private val dispatcherDefault: CoroutineDispatcher = Dispatchers.Default
): ILoggedUserRepository {
    override suspend fun refreshLoggedUser(): Boolean {
        credentialRepository.recoverLocalCredential()?.let { credential ->
            val userResponse = loggedUserExternalSource.getLoggedUserData(
                bearerCode = "Bearer ${credential.accessToken}"
            )
            if (userResponse.isSuccessful) {
                userResponse.body()?.data?.let { userData ->
                    storeLoggedUserData(userData)
                }
                return true
            }
        }
        return false
    }

    override suspend fun getLoggedUser(): UserData? {
        return getLoggedUserData()
    }

    private suspend fun storeLoggedUserData(userData: UserData) {
        coroutineScope {
            launch(dispatcherDefault) {
                loggedUserLocalSource.apply {
                    save(LOGGED_USER_DATA_ID, userData.id)
                    save(LOGGED_USER_DATA_VERIFIED, userData.verified)
                    save(LOGGED_USER_DATA_NAME, userData.name)
                    save(LOGGED_USER_DATA_USERNAME, userData.username)
                    save(LOGGED_USER_DATA_PROFILE_IMAGE_URL, userData.profileImageUrl)
                }
            }
        }
    }

    private fun getLoggedUserData(): UserData {
        loggedUserLocalSource.apply {
            return UserData(
                id = getString(LOGGED_USER_DATA_ID) ?: "",
                verified = getBoolean(LOGGED_USER_DATA_VERIFIED),
                name = getString(LOGGED_USER_DATA_NAME) ?: "",
                username =  getString(LOGGED_USER_DATA_USERNAME) ?: "",
                profileImageUrl =getString(LOGGED_USER_DATA_PROFILE_IMAGE_URL) ?: ""
            )
        }
    }
}