package cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun UpdateContactPage(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    contactId: String
) {

    val vm = koinViewModel<UpdateContactViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(true) {
        onEvent(UpdateContactEvent.OnGetContactById(contactId))
    }

    LaunchedEffect(state.message) {

        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }

    }

    UpdateContactBody(contactId, state, onEvent)

}

@Composable
fun UpdateContactBody(
    contactId: String,
    state: UpdateContactState,
    onEvent: (event: UpdateContactEvent) -> Unit
) {

    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        TextField(value = state.name, onValueChange = {
            onEvent(UpdateContactEvent.OnNameChange(it))
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text("Nom")
        })

        TextField(value = state.email, onValueChange = {
            onEvent(UpdateContactEvent.OnEmailChange(it))
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text("Email")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
        )

        TextField(value = state.numberPhone, onValueChange = {
            onEvent(UpdateContactEvent.OnNumberPhoneChange(it))
        }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ), label = {
            Text("Telephone")
        })

        Button(onClick = {
            onEvent(UpdateContactEvent.OnUpdatedContact(contactId))
        }, modifier = Modifier.fillMaxWidth(), enabled = !state.isLoading) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(30.dp))
            } else {
                Text("Ajouter le contact")
            }
        }

    }


}