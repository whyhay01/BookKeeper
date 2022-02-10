package com.example.bookkeeper.RoomDB

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*


@Entity(tableName = "books")
@Parcelize
class Book(@PrimaryKey val id: String,

           @ColumnInfo(name = "author")
           val authorName: String?,

           val bookName: String?,

           val description: String?,

           @ColumnInfo(name = "last_updated")
           val lastUpdated: Date?

) : Parcelable