package cd.zgeniuscoders.contactappfirebase.contact.domain.utilis

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(
    val route: String
){

    @Serializable
    data object ContactNavGraph: Routes("contact_nav_graph")

    @Serializable
    data object ContactListPage: Routes("contact_list_page")

    @Serializable
    data object AddContactPage: Routes("add_contact_page")

    @Serializable
    data class UpdateContactPage(
        val id: String
    ) : Routes("update_contact_page")

    @Serializable
    data class ContactDetailsPage(
        val id: String
    ) : Routes("detail_contact_page")


}
