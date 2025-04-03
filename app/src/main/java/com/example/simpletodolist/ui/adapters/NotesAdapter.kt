package com.example.simpletodolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletodolist.model.Note
import com.example.simpletodolist.model.Priority
import com.example.simpletodolist.R

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var notes: List<Note> = ArrayList()
    private var onNoteClickListener: OnNoteClickListener? = null

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNotes() : List<Note>{
        return ArrayList(notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.note_item,
                parent,
                false
            )
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note: Note = notes[position]

        holder.textViewNote.apply {
            text = note.text
            setBackgroundColor(
                ContextCompat.getColor(
                    context, when (note.priority) {
                        Priority.LOW -> android.R.color.holo_green_light
                        Priority.MEDIUM -> android.R.color.holo_orange_light
                        Priority.HIGH -> android.R.color.holo_red_light
                    }
                )
            )
        }

        holder.itemView.setOnClickListener {
            onNoteClickListener?.onNoteClick(note)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val textViewNote: TextView = view.findViewById(R.id.textViewNote)
    }

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
    }

}