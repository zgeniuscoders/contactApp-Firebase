package cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cd.zgeniuscoders.contactappfirebase.ui.theme.ContactAppFirebaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddContactPage(navHostController: NavHostController, snackbarHostState: SnackbarHostState) {
    val vm = koinViewModel<AddContactViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(state.message) {
        if(state.message.isNotBlank()){
            snackbarHostState.showSnackbar(state.message)
        }
    }

    AddContactBody(state, onEvent)
}

@Composable
fun AddContactBody(state: AddContactState, onEvent: (event: AddContactEvent) -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(value = state.name, onValueChange = {
            onEvent(AddContactEvent.OnNameChange(it))
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text("Nom")
        })

        TextField(value = state.email, onValueChange = {
            onEvent(AddContactEvent.OnEmailChange(it))
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text("Email")
        })

        TextField(value = state.numberPhone, onValueChange = {
            onEvent(AddContactEvent.OnNumberPhoneChange(it))
        }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ), label = {
            Text("Telephone")
        })

        Button(onClick = {
            onEvent(AddContactEvent.OnFormSubmit)
        }, modifier = Modifier.fillMaxWidth(), enabled = !state.isLoading) {
            Text("Ajouter le contact")
        }
    }
}

@PreviewLightDark
@Composable
fun AddContactPreview(modifier: Modifier = Modifier) {
    ContactAppFirebaseTheme {
        Scaffold { innerP ->
            AddContactBody(state = AddContactState()) {

            }
        }
    }
}