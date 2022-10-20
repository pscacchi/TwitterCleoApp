package ar.scacchipa.twittercloneapp.data.repository

import ar.scacchipa.twittercloneapp.data.datasource.ILocalSource
import ar.scacchipa.twittercloneapp.data.datasource.IOwnerUserExternalSource
import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.OWNER_USER_DATA_ID
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.OWNER_USER_DATA_NAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.OWNER_USER_DATA_PROFILE_IMAGE_URL
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.OWNER_USER_DATA_USERNAME
import ar.scacchipa.twittercloneapp.utils.Constants.Companion.OWNER_USER_DATA_VERIFIED

class OwnerUserRepository(
    private val ownerUserLocalSource: ILocalSource,
    private val ownerUserExternalSource: IOwnerUserExternalSource,
    private val credentialRepository: ICredentialRepository
): IOwnerUserRepository {
    override suspend fun refreshOwnerUser(): Boolean {
        credentialRepository.recoverLocalCredential()?.let { credential ->
            val userResponse = ownerUserExternalSource.getOwnerUserData(
                bearerCode = "Bearer ${credential.accessToken}"
            )
            if (userResponse.isSuccessful) {
                userResponse.body()?.data?.let { userData ->
                    storeOwnerUserData(userData)
                }
                return true
            }
        }
        return false
    }

    override suspend fun getOwnerUser(): UserData? {
        return getOwnerUserData()
    }

    private fun storeOwnerUserData(userData: UserData) {
        ownerUserLocalSource.apply {
            save(OWNER_USER_DATA_ID, userData.id)
            save(OWNER_USER_DATA_VERIFIED, userData.verified)
            save(OWNER_USER_DATA_NAME, userData.name)
            save(OWNER_USER_DATA_USERNAME, userData.username)
            save(OWNER_USER_DATA_PROFILE_IMAGE_URL, userData.profileImageUrl)
        }
    }

    private fun getOwnerUserData(): UserData {
        ownerUserLocalSource.apply {
            return UserData(
                id = getString(OWNER_USER_DATA_ID) ?: "",
                verified = getBoolean(OWNER_USER_DATA_VERIFIED),
                name = getString(OWNER_USER_DATA_NAME) ?: "",
                username =  getString(OWNER_USER_DATA_USERNAME) ?: "",
                profileImageUrl =getString(OWNER_USER_DATA_PROFILE_IMAGE_URL) ?: ""
            )
        }
    }
}