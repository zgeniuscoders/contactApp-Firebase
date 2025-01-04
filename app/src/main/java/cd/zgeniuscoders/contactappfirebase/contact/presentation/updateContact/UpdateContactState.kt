package cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact

data class UpdateContactState(
    val name: String = "",
    val email: String = "",
    val numberPhone: String = "",
    val isLoading: Boolean = false,
    val message: String = ""
)
