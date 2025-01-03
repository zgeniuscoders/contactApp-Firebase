package cd.zgeniuscoders.contactappfirebase.contact.domain.models

data class ContactRequest(
    var id: String = "",
    val name: String,
    val email: String,
    val numberPhone: String
)
