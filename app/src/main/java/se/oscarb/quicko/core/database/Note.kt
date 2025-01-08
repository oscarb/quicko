package se.oscarb.quicko.core.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Note(
    val content: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY uid DESC")
    fun getNotes(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note)
}