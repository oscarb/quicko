package se.oscarb.quicko.core.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "note")
data class LocalNote(
    val content: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY uid DESC")
    fun getNotes(): Flow<List<LocalNote>>

    @Insert
    suspend fun insert(note: LocalNote)
}