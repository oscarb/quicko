package se.oscarb.quicko.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalNote::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}