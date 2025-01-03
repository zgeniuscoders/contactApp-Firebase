package cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact

sealed interface AddContactEvent {


    data class OnNameChange(val value:String): AddContactEvent
    data class OnEmailChange(val value:String): AddContactEvent
    data class OnNumberPhoneChange(val value:String): AddContactEvent

    data object OnFormSubmit: AddContactEvent

}