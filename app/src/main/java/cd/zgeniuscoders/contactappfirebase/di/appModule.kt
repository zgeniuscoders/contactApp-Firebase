package cd.zgeniuscoders.contactappfirebase.di

import cd.zgeniuscoders.contactappfirebase.contact.data.networking.FirebaseContactRepositoryImpl
import cd.zgeniuscoders.contactappfirebase.contact.domain.repository.ContactRepository
import cd.zgeniuscoders.contactappfirebase.contact.domain.usecases.ContactInteractor
import cd.zgeniuscoders.contactappfirebase.contact.presentation.contactList.ContactListViewModel
import com.google.firebase.firestore.FirebaseFirestore
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

    single {
        ContactListViewModel(get<ContactInteractor>())
    }


}