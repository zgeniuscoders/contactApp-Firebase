package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

class GetContactById(
    private val repository: ContactRepository
) {

    suspend fun run(id: String): Flow<Resource<ContactDto>> {
        return repository.getContactById(id)
    }

}