package cd.zgeniuscoders.contactappfirebase.di

import androidx.lifecycle.viewmodel.compose.viewModel
import cd.zgeniuscoders.contactappfirebase.contact.data.network.FirebaseContactRepositoryImpl
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.usecases.ContactInteractor
import cd.zgeniuscoders.contactappfirebase.contact.presentation.addContact.AddContactViewModel
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactDetails.ContactDetailViewModel
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList.ContactListViewModel
import cd.zgeniuscoders.contactappfirebase.contact.presentation.updateContact.UpdateContactViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<ContactRepository> {

        FirebaseContactRepositoryImpl(get())

    }

    single<ContactInteractor> {

        ContactInteractor.build(get())

    }

    viewModelOf(::ContactListViewModel)
    viewModelOf(::AddContactViewModel)
    viewModelOf(::UpdateContactViewModel)
    viewModelOf(::ContactDetailViewModel)

}