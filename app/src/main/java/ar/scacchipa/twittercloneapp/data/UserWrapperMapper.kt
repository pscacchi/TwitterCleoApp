package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.UserData
import ar.scacchipa.twittercloneapp.data.model.response.UserDataWrapper
import ar.scacchipa.twittercloneapp.domain.model.UserInfo

class UserWrapperMapper(
    private val userMapper: IMapper<UserData, UserInfo>
): IMapper<UserDataWrapper, UserInfo> {
    override fun toDomain(value: UserDataWrapper): UserInfo {
        return userMapper.toDomain(value.data)
    }

    override fun fromDomain(value: UserInfo): UserDataWrapper {
        return UserDataWrapper(
            data = userMapper.fromDomain(value)
        )
    }
}

