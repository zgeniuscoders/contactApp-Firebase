package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.Contact

data class ContactDetailState(
    val contact: Contact? = null,
    val isLoading: Boolean = false,
    val message: String = ""
)
