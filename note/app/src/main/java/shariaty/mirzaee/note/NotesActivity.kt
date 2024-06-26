package shariaty.mirzaee.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.Activity_notes)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val addNoteButton = findViewById<Button>(R.id.add_note_button)
        val darkModeButton = findViewById<Button>(R.id.dark_mode_button)
        val lightModeButton = findViewById<Button>(R.id.light_mode_button)
        val notesList = findViewById<ListView>(R.id.notes_list)

        addNoteButton.setOnClickListener {
            addNote()
        }

        darkModeButton.setOnClickListener {
        }

        lightModeButton.setOnClickListener {
        }

        loadNotes()
    }

    private fun addNote() {
        val note = hashMapOf(
            "content" to "New Note",
            "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        )
        db.collection("notes")
            .add(note)
            .addOnSuccessListener {
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                loadNotes()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadNotes() {
        db.collection("notes").orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val notes = result.map { it.data["content"] as String }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load notes", Toast.LENGTH_SHORT).show()
            }
    }
}