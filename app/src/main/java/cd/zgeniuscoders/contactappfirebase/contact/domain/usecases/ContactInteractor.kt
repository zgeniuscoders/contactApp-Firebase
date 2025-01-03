package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository

class ContactInteractor(
    val addContact: AddContact,
    val updateContact: UpdateContact,
    val deleteContact: DeleteContact,
    val getContacts: GetContacts,
    val getContactById: GetContactById
) {

    companion object{
        fun build(repository: ContactRepository): ContactInteractor {
            return ContactInteractor(
                AddContact(repository),
                UpdateContact(repository),
                DeleteContact(repository),
                GetContacts(repository),
                GetContactById(repository)
            )
        }
    }


}