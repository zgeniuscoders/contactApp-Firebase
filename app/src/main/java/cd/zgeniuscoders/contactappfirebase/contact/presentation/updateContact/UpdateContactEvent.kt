package cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact

sealed interface UpdateContactEvent {

    data class OnNameChange(val value:String): UpdateContactEvent
    data class OnEmailChange(val value:String): UpdateContactEvent
    data class OnNumberPhoneChange(val value:String): UpdateContactEvent

    data class OnUpdatedContact(val id: String): UpdateContactEvent
    data class OnGetContactById(val id: String): UpdateContactEvent

}