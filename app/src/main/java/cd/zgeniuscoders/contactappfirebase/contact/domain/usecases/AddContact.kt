package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

class AddContact(
    private val repository: ContactRepository
) {

    suspend fun run(request: ContactRequest): Flow<Resource<Boolean>> {
        return repository.addContact(request)
    }

}