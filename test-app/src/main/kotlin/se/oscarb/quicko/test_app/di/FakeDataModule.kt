package se.oscarb.quicko.test_app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import se.oscarb.quicko.core.data.NoteRepository
import se.oscarb.quicko.core.data.di.DataModule
import se.oscarb.quicko.core.data.di.FakeNoteRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface FakeDataModule {

    @Binds
    fun bindRepository(fakeRepository: FakeNoteRepository): NoteRepository
}