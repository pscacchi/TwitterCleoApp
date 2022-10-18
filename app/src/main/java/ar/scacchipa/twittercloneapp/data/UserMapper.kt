package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.UserWrapper
import ar.scacchipa.twittercloneapp.data.model.UsersData
import ar.scacchipa.twittercloneapp.domain.model.UserInfo

class UserMapper: IMapper<UserWrapper, UserInfo> {
    override fun toDomain(value: UserWrapper): UserInfo {
        return UserInfo(
            id = value.data.id,
        )
    }

    override fun fromDomain(value: UserInfo): UserWrapper {
        return UserWrapper(
            data = UsersData(
                id = value.id,
                verified = false,
                name = "",
                username = "",
                profileImageUrl = ""
            )
        )
    }
}
