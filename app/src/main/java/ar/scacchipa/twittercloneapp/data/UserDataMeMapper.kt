package ar.scacchipa.twittercloneapp.data

import ar.scacchipa.twittercloneapp.data.model.me.UserMeData
import ar.scacchipa.twittercloneapp.data.model.me.UserMeWrapper
import ar.scacchipa.twittercloneapp.domain.model.UserMeInfo

class UserDataMeMapper: IMapper<UserMeWrapper, UserMeInfo> {
    override fun toDomain(value: UserMeWrapper): UserMeInfo {
        return UserMeInfo(
            id = value.data.id
        )
    }

    override fun fromDomain(value: UserMeInfo): UserMeWrapper {
        return UserMeWrapper(
            data = UserMeData(
                id = value.id
            )
        )
    }
}
