package com.example.ontime.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ontime.MainActivity
import com.example.ontime.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                saveUserToDatabase(email)
                            } else {
                                Toast.makeText(this, it.exception?.localizedMessage ?: "Signup failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveUserToDatabase(email: String) {
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid?: return

        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("useres")
        val userMap = mapOf(
            "email" to email,
            "uid" to uid
        )

        usersRef.child(uid).setValue(userMap).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        firebaseAuth.currentUser?.let {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}