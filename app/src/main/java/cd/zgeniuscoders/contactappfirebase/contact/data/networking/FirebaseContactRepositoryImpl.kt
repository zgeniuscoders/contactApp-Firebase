package cd.zgeniuscoders.contactappfirebase.contact.data.networking

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.Contact
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactsDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseContactRepositoryImpl(
    private val db: FirebaseFirestore
) : ContactRepository {

    private val collection = db.collection("contacts")

    override suspend fun addContact(request: ContactRequest): Flow<Resource<Boolean>> =
        callbackFlow {
            try {

                withContext(Dispatchers.IO) {
                    val docId = collection.document().id
                    request.id = docId

                    collection.document(docId).set(request).await()
                    trySend(
                        Resource.Success(
                            data = true
                        )
                    )
                }

            } catch (e: Exception) {
                trySend(
                    Resource.Error(
                        message = e.message.toString()
                    )
                )
            }

            awaitClose()
        }

    override suspend fun deleteContact(id: String): Flow<Resource<Boolean>> = callbackFlow {
        try {

            withContext(Dispatchers.IO) {
                collection.document(id).delete().await()
                trySend(
                    Resource.Success(
                        data = true
                    )
                )
            }

        } catch (e: Exception) {
            trySend(
                Resource.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun updateContact(
        id: String,
        request: ContactRequest
    ): Flow<Resource<Boolean>> = callbackFlow {
        try {

            withContext(Dispatchers.IO) {
                collection.document(id).set(request).await()
                trySend(
                    Resource.Success(
                        data = true
                    )
                )
            }

        } catch (e: Exception) {
            trySend(
                Resource.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun getContacts(): Flow<Resource<ContactsDto>> = callbackFlow {
        try {

            withContext(Dispatchers.IO) {
                collection.addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Resource.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        val contacts = value.toObjects(Contact::class.java)
                        trySend(
                            Resource.Success(
                                data = ContactsDto(
                                    data = contacts
                                )
                            )
                        )
                    }

                }

            }

        } catch (e: Exception) {
            trySend(
                Resource.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }

    override suspend fun getContactById(id: String): Flow<Resource<ContactDto>> = callbackFlow {
        try {

            withContext(Dispatchers.IO) {
                collection.document(id).addSnapshotListener { value, error ->

                    if (error != null) {
                        trySend(
                            Resource.Error(
                                message = error.message.toString()
                            )
                        )
                    }

                    if (value != null) {
                        val contact = value.toObject(Contact::class.java)
                        trySend(
                            Resource.Success(
                                data = ContactDto(
                                    data = contact!!
                                )
                            )
                        )
                    }

                }

            }

        } catch (e: Exception) {
            trySend(
                Resource.Error(
                    message = e.message.toString()
                )
            )
        }

        awaitClose()
    }
}