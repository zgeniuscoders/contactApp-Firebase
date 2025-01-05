package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cd.zgeniuscoders.contactappfirebase.contact.domain.usecases.ContactInteractor
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Resource
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactDetailViewModel(
    private val contactInteractor: ContactInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val contactId = savedStateHandle.toRoute<Routes.ContactDetailsPage>().id

    init {
        Log.i("VM", "vm init")
    }


    private val _state = MutableStateFlow(ContactDetailState())
    val state = _state
        .onStart {
            getContactById()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onTriggerEvent(event: ContactDetailEvent) {
        when (event) {
            is ContactDetailEvent.OnGetContactById -> getContactById()
        }
    }

    private fun getContactById() {

        Log.e("CONTACT_ID", contactId)

        viewModelScope.launch {


            _state.update {
                it.copy(isLoading = true)
            }

            val res = contactInteractor
                .getContactById
                .run(contactId)

            when (res) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoading = false, message = res.message.toString())
                    }

                }

                is Resource.Success -> {


                    val contact = res.data!!.data

                    _state.update {
                        it.copy(
                            isLoading = false,
                            contact = contact
                        )
                    }

                }

            }

        }

    }

}