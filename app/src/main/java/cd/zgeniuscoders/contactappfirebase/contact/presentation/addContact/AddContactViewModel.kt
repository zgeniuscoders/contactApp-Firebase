package cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact

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

class AddContactViewModel(
    private val contactInteractor: ContactInteractor
) : ViewModel() {

    var state by mutableStateOf(AddContactState())
        private set


    fun onTriggerEvent(event: AddContactEvent) {
        when (event) {
            is AddContactEvent.OnEmailChange -> state = state.copy(email = event.value)
            AddContactEvent.OnFormSubmit -> addContact()
            is AddContactEvent.OnNameChange -> state = state.copy(name = event.value)
            is AddContactEvent.OnNumberPhoneChange -> state = state.copy(numberPhone = event.value)
        }
    }

    private fun addContact() {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true)
            }

            val res = contactInteractor
                .addContact
                .run(
                    ContactRequest(
                        name = state.name,
                        email = state.email,
                        numberPhone = state.numberPhone
                    )
                )

            when (res) {
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        state =
                            state.copy(message = res.message.toString(), isLoading = false)
                    }
                }

                is Resource.Success -> {
                    withContext(Dispatchers.Main) {
                        state = state.copy(
                            isLoading = false,
                            name = "",
                            email = "",
                            numberPhone = ""
                        )
                    }
                }
            }

        }
    }

}