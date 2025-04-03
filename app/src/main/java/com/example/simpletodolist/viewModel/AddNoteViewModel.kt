package com.example.simpletodolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpletodolist.model.Note
import com.example.simpletodolist.model.NoteDatabase

class AddNoteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val db = NoteDatabase.getDatabase(application)

    private val shouldCloseScreen : MutableLiveData<Boolean> = MutableLiveData()

    fun saveNote(note: Note){
        Thread{
            db.noteDao().add(note)
            shouldCloseScreen.postValue(true)
        }.start()
    }

    fun getShouldCloseScreen() : LiveData<Boolean>{
        return shouldCloseScreen
    }
}