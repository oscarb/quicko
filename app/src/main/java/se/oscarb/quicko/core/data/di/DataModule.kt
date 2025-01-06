package se.oscarb.quicko.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import se.oscarb.quicko.core.data.DefaultNoteRepository
import se.oscarb.quicko.core.data.NoteRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsNoteRepository(noteRepository: DefaultNoteRepository): NoteRepository

}

// TODO Add fake repository for testing