package cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact

data class AddContactState(
    val name: String = "",
    val email: String = "",
    val numberPhone: String = "",
    val isLoading: Boolean = false,
    val message: String = "",
    val formHasError: Boolean = true
)
