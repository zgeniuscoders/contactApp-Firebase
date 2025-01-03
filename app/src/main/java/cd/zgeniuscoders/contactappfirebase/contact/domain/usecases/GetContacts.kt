package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactsDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

class GetContacts(
    private val repository: ContactRepository

) {

    suspend fun run(): Flow<Resource<ContactsDto>> {
        return repository.getContacts()
    }

}