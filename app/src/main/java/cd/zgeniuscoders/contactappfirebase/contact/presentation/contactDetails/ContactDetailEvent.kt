package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

sealed interface ContactDetailEvent {

    data class OnGetContactById(val id: String) : ContactDetailEvent

}