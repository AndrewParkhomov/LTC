package parkhomov.andrew.ltc.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refractive_index")
data class RefractiveIndexEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val value: Double,
    val label: String,
    val isUserCreated: Boolean = false
)
