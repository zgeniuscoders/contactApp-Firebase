package cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.ContactRequest
import cd.zgeniuscoders.contactappfirebase.contact.domain.usecases.ContactInteractor
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateContactViewModel(
    private val contactInteractor: ContactInteractor
) : ViewModel() {

    var state by mutableStateOf(UpdateContactState())
        private set


    fun onTriggerEvent(event: UpdateContactEvent) {
        when (event) {
            is UpdateContactEvent.OnEmailChange -> state = state.copy(email = event.value)
            is UpdateContactEvent.OnUpdatedContact -> updateContact(event.id)
            is UpdateContactEvent.OnNameChange -> state = state.copy(name = event.value)
            is UpdateContactEvent.OnNumberPhoneChange -> state =
                state.copy(numberPhone = event.value)

            is UpdateContactEvent.OnGetContactById -> getContactById(event.id)
        }
    }

    private fun getContactById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true)
            }

            val res = contactInteractor
                .getContactById
                .run(id)

            when (res) {
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        state =
                            state.copy(isLoading = false, message = res.message.toString())
                    }
                }

                is Resource.Success -> {
                    withContext(Dispatchers.Main) {

                        val contact = res.data!!.data

                        state = state.copy(
                            isLoading = false,
                            name = contact.name,
                            email = contact.email,
                            numberPhone = contact.numberPhone
                        )
                    }
                }
            }

        }
    }

    private fun updateContact(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true)
            }

            val res = contactInteractor
                .updateContact
                .run(
                    id, ContactRequest(
                        id,
                        state.name,
                        state.email,
                        state.numberPhone
                    )
                )

            when (res) {
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        state =
                            state.copy(isLoading = false, message = res.message.toString())
                    }
                }

                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            isLoading = false,
                            message = "Contact updated successfully"
                        )
                    }
                }
            }

        }
    }

}