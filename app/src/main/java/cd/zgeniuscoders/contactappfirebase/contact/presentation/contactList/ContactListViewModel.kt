package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList

import android.util.Log
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

class ContactListViewModel(
    private val contactInteractor: ContactInteractor
) : ViewModel() {


    var state by mutableStateOf(ContactListState())
        private set

    init {
        getContacts()
    }

    fun onTriggerEvent(event: ContactListEvent) {
        when (event) {
            is ContactListEvent.OnDeleteContact -> deleteContact(event.id)
        }
    }

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactInteractor
                .getContacts
                .run()
                .onEach { res ->

                    state = when (res) {
                        is Resource.Error -> {
                            withContext(Dispatchers.Main){
                                state.copy(isLoading = false, message = res.message.toString())
                            }
                        }

                        is Resource.Success -> {
                            withContext(Dispatchers.Main){
                                state.copy(isLoading = false, contacts = res.data!!.data)
                            }
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }

    private fun deleteContact(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main){
                state = state.copy(isLoading = true)
            }

            contactInteractor
                .deleteContact
                .run(id)
                .onEach { res ->

                    when(res){
                        is Resource.Error -> {
                            withContext(Dispatchers.Main){
                                state = state.copy(isLoading = false, message = res.message.toString())
                            }

                        }
                        is Resource.Success -> {
                            withContext(Dispatchers.Main){
                                state = state.copy(isLoading = false)
                            }

                        }
                    }

                }
                .launchIn(viewModelScope)
        }
    }
}