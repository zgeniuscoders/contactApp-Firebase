package cd.zgeniuscoders.contactappfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Routes
import cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact.AddContactPage
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails.ContactDetailPage
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList.ContactListPage
import cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact.UpdateContactPage
import cd.zgeniuscoders.contactappfirebase.ui.theme.ContactAppFirebaseTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ContactAppFirebaseTheme {

                val navHostController = rememberNavController()
                val snackbarHostState = SnackbarHostState()

                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        when (currentRoute) {
                            Routes.ContactListPage.route -> CenterAlignedTopAppBar(title = { Text("Contact App") })
                            else -> CenterAlignedTopAppBar(navigationIcon = {
                                IconButton(onClick = { navHostController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = "ic_arrow_back"
                                    )
                                }
                            }, title = {
                                Text(
                                    when (currentRoute) {
                                        Routes.AddContactPage.route -> "Ajout d'un contact"
                                        else -> "Modification d'un contact"
                                    }
                                )
                            })
                        }
                    },
                    floatingActionButton = {
                        if (currentRoute == Routes.ContactListPage.route) {
                            FloatingActionButton(onClick = { navHostController.navigate(Routes.AddContactPage.route) }) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = "ic_person_add",
                                )
                            }
                        }
                    }
                ) { innerP ->
                    NavHost(
                        modifier = Modifier.padding(innerP),
                        navController = navHostController,
                        startDestination = Routes.ContactNavGraph){

                        navigation<Routes.ContactNavGraph>(
                            startDestination = Routes.ContactListPage
                        ) {
                            composable<Routes.ContactListPage> {
                                ContactListPage(navHostController)
                            }
                        }

                        composable<Routes.AddContactPage> {
                            AddContactPage(navHostController, snackbarHostState)
                        }

                        composable<Routes.UpdateContactPage> {
                            val ars = it.toRoute<Routes.UpdateContactPage>()
                            val contactId = ars.id

                            UpdateContactPage(
                                navHostController = navHostController,
                                snackbarHostState = snackbarHostState,
                                contactId = contactId
                            )
                        }

                        composable<Routes.ContactDetailsPage> {
                            val ars = it.toRoute<Routes.UpdateContactPage>()

                            ContactDetailPage(
                                navHostController = navHostController,
                                snackbarHostState = snackbarHostState,
                            )
                        }

                    }
                }
            }
        }
    }
}

