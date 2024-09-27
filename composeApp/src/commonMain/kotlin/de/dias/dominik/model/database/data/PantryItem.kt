package de.dias.dominik.model.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PantryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "mhd") val mhd: Long,
    @ColumnInfo(name = "ean") val ean: String,
    @ColumnInfo(name = "url") val url: String,
)
