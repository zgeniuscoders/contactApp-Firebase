package cd.zgeniuscoders.contactappfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.contactappfirebase.contact.domain.utilis.Routes
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList.ContactListPage
import cd.zgeniuscoders.contactappfirebase.ui.theme.ContactAppFirebaseTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppFirebaseTheme {
                val navHostController = rememberNavController()
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
                    NavHost(
                        modifier = Modifier.padding(innerP),
                        navController = navHostController,
                        startDestination = Routes.ContactListPage.route){

                        composable(route = Routes.ContactListPage.route) {
                            ContactListPage(navHostController)
                        }

                    }
                }
            }
        }
    }
}
