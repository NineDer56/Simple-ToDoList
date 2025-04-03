package com.example.simpletodolist.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.simpletodolist.model.Note
import com.example.simpletodolist.model.Priority
import com.example.simpletodolist.R
import com.example.simpletodolist.viewModel.AddNoteViewModel


class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextNote : EditText

    private lateinit var radioGroupPriority: RadioGroup
    private lateinit var radioButtonLow: RadioButton
    private lateinit var radioButtonMedium: RadioButton
    private lateinit var radioButtonHigh: RadioButton

    private lateinit var buttonSave: Button

    private lateinit var viewModel: AddNoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        viewModel.getShouldCloseScreen().observe(this){
            if(it) finish()
        }

        init()
        setOnClickListeners()
    }


    private fun setOnClickListeners(){
        buttonSave.setOnClickListener {
            val text = editTextNote.text.toString().trim()
            val priority = getButtonPriority()

            viewModel.saveNote(Note(text = text, priority = priority))
        }
    }

    private fun init(){
        editTextNote = findViewById(R.id.editTextNote)
        radioGroupPriority = findViewById(R.id.radioGroupPriority)
        radioButtonLow = findViewById(R.id.radioButtonLow)
        radioButtonMedium = findViewById(R.id.radioButtonMedium)
        radioButtonHigh = findViewById(R.id.radioButtonHigh)
        buttonSave = findViewById(R.id.buttonSave)
    }

    private fun getButtonPriority() : Priority {
        val priority =  if (radioButtonLow.isChecked) Priority.LOW
        else if (radioButtonMedium.isChecked) Priority.MEDIUM
        else Priority.HIGH

        return priority
    }

    companion object{
        fun newIntent(context : Context) = Intent(context, AddNoteActivity::class.java)
    }
}