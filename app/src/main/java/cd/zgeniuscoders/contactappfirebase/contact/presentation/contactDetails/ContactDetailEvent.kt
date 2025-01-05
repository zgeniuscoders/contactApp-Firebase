package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

sealed interface ContactDetailEvent {

    data object OnGetContactById : ContactDetailEvent

}