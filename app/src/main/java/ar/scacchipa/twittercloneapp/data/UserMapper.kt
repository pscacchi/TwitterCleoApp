package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.domain.model.UserInfo

class UserMapper: IMapper<UserData, UserInfo> {
    override fun toDomain(value: UserData): UserInfo {
        return UserInfo(
            id = value.id,
            verified = value.verified,
            name = value.name,
            username = value.username,
            profileImageUrl = value.profileImageUrl
        )
    }

    override fun fromDomain(value: UserInfo): UserData {
        return UserData(
            id = value.id,
            verified = value.verified,
            name = value.name,
            username = value.username,
            profileImageUrl = value.profileImageUrl
        )
    }
}