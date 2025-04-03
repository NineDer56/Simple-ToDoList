package com.example.simpletodolist.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.ui.adapters.NotesAdapter
import com.example.simpletodolist.R
import com.example.simpletodolist.viewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var buttonAdd: FloatingActionButton

    private lateinit var notesAdapter: NotesAdapter

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включает режим "edge-to-edge", при котором контент отображается под системными барами (status bar и navigation bar).
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // Устанавливает слушатель для обработки WindowInsets (отступов системных элементов, таких как status bar и navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initViews()
        setOnClickListeners()


        notesAdapter = NotesAdapter()
        recyclerViewNotes.adapter = notesAdapter

        viewModel.getNotes().observe(this
        ) {
            notesAdapter.setNotes(it)
        }

        val itemTouchHelper = getItemTouchHelper()
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes)
    }



    private fun getItemTouchHelper() : ItemTouchHelper{
        val itemTouchHelper: ItemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = notesAdapter.getNotes()[position]
                    viewModel.remove(note)
                }
            })
        return itemTouchHelper
    }

    private fun setOnClickListeners() {
        buttonAdd.setOnClickListener {
            val intent = AddNoteActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        buttonAdd = findViewById(R.id.floatingButtonAdd)
    }
}