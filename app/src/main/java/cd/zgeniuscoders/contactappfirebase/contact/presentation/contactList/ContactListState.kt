package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.Contact

data class ContactListState(
    val isLoading: Boolean = true,
    val contacts: List<Contact> = emptyList(),
    val message: String = ""
)
