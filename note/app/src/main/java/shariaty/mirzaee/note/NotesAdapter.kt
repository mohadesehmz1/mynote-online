package shariaty.mirzaee.note


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class NotesAdapter(private val context: Context, private val notes: List<Map<String, Any>>, private val db: FirebaseFirestore) : BaseAdapter() {

    override fun getCount(): Int {
        return notes.size
    }

    override fun getItem(position: Int): Any {
        return notes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false)

        val noteContent = view.findViewById<TextView>(R.id.note_content)
        val updateNoteButton = view.findViewById<Button>(R.id.update_note_button)
        val deleteNoteButton = view.findViewById<Button>(R.id.delete_note_button)

        val note = notes[position]
        noteContent.text = note["content"] as String

        updateNoteButton.setOnClickListener {
            // منطق به‌روزرسانی یادداشت
            val newContent = "Updated Note"
            val noteId = note["id"] as String
            db.collection("notes").document(noteId)
                .update("content", newContent)
                .addOnSuccessListener {
                    Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to update note", Toast.LENGTH_SHORT).show()
                }
        }

        deleteNoteButton.setOnClickListener {
            val noteId = note["id"] as String
            db.collection("notes").document(noteId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show()
                }
        }

        return view
    }
}
