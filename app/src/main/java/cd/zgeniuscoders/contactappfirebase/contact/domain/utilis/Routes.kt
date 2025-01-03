package cd.zgeniuscoders.contactappfirebase.contact.domain.utilis

sealed class Routes(
    val route: String
){

    data object ContactListPage: Routes("contact_list_page")

    data object AddContactPage: Routes("add_contact_page")

    data object UpdateContactPage: Routes("update_contact_page")

    data object ContactDetailsPage: Routes("detail_contact_page")


}
