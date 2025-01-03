package cd.zgeniuscoders.contactappfirebase.contact.domain.models

data class ContactDto(
    val data: Contact,
    val error: String? = null
)

data class ContactsDto(
    val data: List<Contact>
)

