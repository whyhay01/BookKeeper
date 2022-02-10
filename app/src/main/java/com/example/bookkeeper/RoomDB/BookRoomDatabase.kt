package com.example.bookkeeper.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bookkeeper.typeConverters.DateTypeConverter

@Database(entities = [Book::class], version = 4)
@TypeConverters(DateTypeConverter::class)
abstract class BookRoomDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao



    companion object{
        private var bookRoomInstance: BookRoomDatabase? = null

        private val MIGRATION_2_3 = object: Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books "
                        + " ADD COLUMN description TEXT")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3,4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books "
                + "ADD COLUMN last_updated INTEGER")
            }

        }



        fun getDatabase(context: Context): BookRoomDatabase? {
            if (bookRoomInstance == null){

                synchronized(BookRoomDatabase::class.java){
                    if (bookRoomInstance == null){
                        bookRoomInstance = Room.databaseBuilder(context.applicationContext,
                        BookRoomDatabase::class.java, "book_database")
                            .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                            .build()
                    }
                }
            }

            //fallbackToDestructiveMigration()

            return bookRoomInstance
        }
    }
}