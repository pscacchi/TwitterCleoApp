package ar.scacchipa.twittercloneapp.domain.model

sealed interface  ReferencedType {

    data class RetweetedType(
        val name: String
    ) : ReferencedType

    data class RepliedToType(
        val name: String
    ) : ReferencedType

    data class QuotedType(
        val name: String
    ): ReferencedType

    object NoReferencedType : ReferencedType
}