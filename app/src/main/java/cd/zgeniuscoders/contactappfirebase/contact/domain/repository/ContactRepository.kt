package cd.zgeniuscoders.contactappfirebase.contact.domain.repository

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactsDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun addContact(request: ContactRequest): Resource<Boolean>

    suspend fun deleteContact(id: String): Resource<Boolean>

    suspend fun updateContact(id: String, request: ContactRequest): Resource<Boolean>

    suspend fun getContacts(): Flow<Resource<ContactsDto>>

    suspend fun getContactById(id: String): Resource<ContactDto>

}