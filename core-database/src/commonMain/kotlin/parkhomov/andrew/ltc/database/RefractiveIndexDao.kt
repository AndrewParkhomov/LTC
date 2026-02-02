package parkhomov.andrew.ltc.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefractiveIndexDao {

    @Query("SELECT * FROM refractive_index ORDER BY value ASC")
    fun getAllIndices(): Flow<List<RefractiveIndexEntity>>

    @Query("SELECT * FROM refractive_index ORDER BY value ASC")
    suspend fun getAllIndicesOnce(): List<RefractiveIndexEntity>

    @Query("SELECT * FROM refractive_index WHERE id = :id")
    suspend fun getById(id: Long): RefractiveIndexEntity?

    @Query("SELECT * FROM refractive_index WHERE value = :value LIMIT 1")
    suspend fun getByValue(value: Double): RefractiveIndexEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(index: RefractiveIndexEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(indices: List<RefractiveIndexEntity>)

    @Delete
    suspend fun delete(index: RefractiveIndexEntity)

    @Query("DELETE FROM refractive_index WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM refractive_index")
    suspend fun getCount(): Int
}
