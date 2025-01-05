package cd.zgeniuscoders.contactappfirebase.contact.domain.usecases

import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

class DeleteContact(
    private val repository: ContactRepository
) {

    suspend fun run(id: String): Resource<Boolean> {
        return repository.deleteContact(id)
    }

}