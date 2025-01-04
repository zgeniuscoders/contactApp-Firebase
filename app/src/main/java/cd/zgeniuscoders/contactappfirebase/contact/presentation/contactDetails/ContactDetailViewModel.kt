package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.contactappfirebase.contact.domain.usecases.ContactInteractor
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactDetailViewModel(
    private val contactInteractor: ContactInteractor
) : ViewModel() {

    var state by mutableStateOf(ContactDetailState())
        private set

    fun onTriggerEvent(event: ContactDetailEvent) {
        when (event) {
            is ContactDetailEvent.OnGetContactById -> getContactById(event.id)
        }
    }

    private fun getContactById(id: String) {

        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true)
            }

            contactInteractor
                .getContactById
                .run(id)
                .onEach { res ->

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
                                    contact = contact
                                )
                            }
                        }
                    }

                }.launchIn(viewModelScope)

        }

    }

}