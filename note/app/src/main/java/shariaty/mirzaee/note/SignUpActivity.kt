package shariaty.mirzaee.note

    import android.content.Intent
    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import com.google.firebase.auth.FirebaseAuth

    class SignUpActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)

            auth = FirebaseAuth.getInstance()

            val name = findViewById<EditText>(R.id.name)
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)
            val signupButton = findViewById<Button>(R.id.signup_button)

            signupButton.setOnClickListener {
                val nameText = name.text.toString()
                val emailText = email.text.toString()
                val passwordText = password.text.toString()

                if (nameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "لطفاً تمام فیلدها را پر کنید", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
