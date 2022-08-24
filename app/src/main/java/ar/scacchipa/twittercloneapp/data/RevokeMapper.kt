package ar.scacchipa.twittercloneapp.data

class RevokeMapper: IMapper<RevokeData, RevokeDomain> {
    override fun toDomain(value: RevokeData): RevokeDomain {
        return RevokeDomain(
            revoked = value.revoked
        )
    }
    override fun fromDomain(value: RevokeDomain): RevokeData {
        return RevokeData(
            revoked = value.revoked
        )
    }
}