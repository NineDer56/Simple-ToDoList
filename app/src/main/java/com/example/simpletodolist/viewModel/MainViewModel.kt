package com.example.simpletodolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.simpletodolist.model.Note
import com.example.simpletodolist.model.NoteDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var db : NoteDatabase = NoteDatabase.getDatabase(application)

    fun getNotes() : LiveData<List<Note>>{
        return db.noteDao().getNotes()
    }

    fun remove(note : Note){
        Thread{
            db.noteDao().delete(note)
        }.start()
    }
}