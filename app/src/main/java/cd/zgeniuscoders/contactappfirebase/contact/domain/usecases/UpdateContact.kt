package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

class UpdateContact(
    private val repository: ContactRepository
) {

    suspend fun run(id: String, request: ContactRequest): Flow<Resource<Boolean>> {
        return repository.updateContact(id, request)
    }

}