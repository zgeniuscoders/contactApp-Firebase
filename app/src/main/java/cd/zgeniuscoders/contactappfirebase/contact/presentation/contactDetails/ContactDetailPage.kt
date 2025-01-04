package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Routes
import cd.zgeniuscoders.contactappfirebase.contact.presentation.components.ContactAvatarInitial
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList.contact
import cd.zgeniuscoders.contactappfirebase.ui.theme.ContactAppFirebaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactDetailPage(
    contactId: String, navHostController: NavHostController, snackbarHostState: SnackbarHostState
) {

    val vm = koinViewModel<ContactDetailViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    LaunchedEffect(true) {
        onEvent(ContactDetailEvent.OnGetContactById(contactId))
    }

    LaunchedEffect(state.message) {

        if (state.message.isNotBlank()) {
            snackbarHostState.showSnackbar(state.message)
        }

    }

    ContactDetailBody(state, navHostController)

}

@Composable
fun ContactDetailBody(state: ContactDetailState, navHostController: NavHostController) {

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(30.dp))
        }
    } else {

        if (state.contact != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ContactAvatarInitial(size = 150.dp, initial = state.contact.name[0])

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    state.contact.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(state.contact.email, style = MaterialTheme.typography.titleLarge)

                Text(state.contact.numberPhone)

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    navHostController.navigate(Routes.UpdateContactPage(state.contact.id))
                }) {
                    Text("Modifier")
                }

            }
        } else {
            Text(text = "Un expected error occurred")
        }

    }

}

@PreviewLightDark
@Composable
fun ContactDetailPreview(modifier: Modifier = Modifier) {
    ContactAppFirebaseTheme {
        Scaffold { innerP ->
            ContactDetailBody(
                state = ContactDetailState(
                    contact = contact
                ), navHostController = rememberNavController()
            )
        }
    }
}