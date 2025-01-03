package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList

sealed interface ContactListEvent {

    data class OnDeleteContact(val id: String) : ContactListEvent

}