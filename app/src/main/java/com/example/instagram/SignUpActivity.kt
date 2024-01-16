package com.example.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagram.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            if(binding.nameField.editText?.text.toString().equals("") or binding.emailField.editText?.text.toString().equals("") or binding.passwordField.editText?.text.toString().equals("")){
                Toast.makeText(this@SignUpActivity, "Please fill fields", Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailField.editText?.text.toString(), binding.passwordField.editText?.text.toString()).addOnCompleteListener {
                    result->
                    if(result.isSuccessful){
                        Toast.makeText(this@SignUpActivity, "Registered", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}