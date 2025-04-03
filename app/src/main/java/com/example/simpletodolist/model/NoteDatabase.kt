package com.example.simpletodolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {
        private var instance: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return instance ?: Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes"
            ).build().also{ instance = it}
        }
    }
}