package cd.zgeniuscoders.contactappfirebase.contact.data.network

import cd.zgeniuscoders.contactappfirebase.contact.domain.models.Contact
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactsDto
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
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

    override suspend fun addContact(request: ContactRequest): Resource<Boolean> {
        return try {

            val docId = collection.document().id
            request.id = docId

            collection.document(docId).set(request).await()

            Resource.Success(data = true)


        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }

    }

    override suspend fun deleteContact(id: String): Resource<Boolean> {
        try {

            collection.document(id).delete().await()
            return Resource.Success(
                data = true
            )


        } catch (e: Exception) {
            return Resource.Error(
                message = e.message.toString()
            )
        }

    }

    override suspend fun updateContact(
        id: String,
        request: ContactRequest
    ): Resource<Boolean> {
        try {

            collection.document(id).set(request).await()
            return Resource.Success(
                data = true
            )

        } catch (e: Exception) {

            return Resource.Error(
                message = e.message.toString()
            )

        }

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

    override suspend fun getContactById(id: String): Resource<ContactDto> {

        return try {
            val deferred = CompletableDeferred<Resource<ContactDto>>()

            collection.document(id).addSnapshotListener { value, error ->

                if (error != null) {
                    deferred.complete(Resource.Error(message = error.message.toString()))
                }

                if (value != null) {
                    val contact = value.toObject(Contact::class.java)
                    deferred.complete(
                        Resource.Success(
                            data = ContactDto(
                                data = contact!!
                            )
                        )
                    )
                }

            }

            deferred.await()
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }

    }
}