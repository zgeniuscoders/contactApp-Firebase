package cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.contactappfirebase.contact.domain.models.Contact
import cd.zgeniuscoders.contactappfirebase.ui.theme.ContactAppFirebaseTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ContactListPage(navHostController: NavHostController) {

    val vm = koinViewModel<ContactListViewModel>()
    val state = vm.state
    val onEvent = vm::onTriggerEvent

    ContactListBody(state, onEvent, navHostController)
}

@Composable
fun ContactListBody(
    state: ContactListState,
    onEvent: (event: ContactListEvent) -> Unit,
    navHostController: NavHostController
) {
    LazyColumn {
        items(state.contacts) { contact ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Card(
                    modifier = Modifier.size(60.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            "${contact.name[0]}",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                Column {
                    Text(
                        contact.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        contact.numberPhone,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "ic_edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { 
                    onEvent(ContactListEvent.OnDeleteContact(contact.id))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "ic_delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun ContactListPreview(modifier: Modifier = Modifier) {
    ContactAppFirebaseTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text("Contact App") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "ic_person_add",
                    )
                }
            }
        ) { innerP ->
            ContactListBody(state = ContactListState(
                contacts = (1..20).map { contact }),
                {},
                rememberNavController()
            )
        }
    }
}

internal val contact = Contact("aa", "zgenius", "zgeniusccoders@gmail.com", "000000")