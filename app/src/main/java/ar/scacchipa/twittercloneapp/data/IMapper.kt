package ar.scacchipa.twittercloneapp.data

interface IMapper<T : Any, R : Any> {
    fun toDomain(value: T): R
    fun fromDomain(value: R): T
}
