package com.example.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding.loginBtn.setOnClickListener {
            if(binding.loginEmail.editText?.text.toString().equals("") or binding.loginPassword.editText?.text.toString().equals("")){
                Toast.makeText(this@LoginActivity, "Fill all fields", Toast.LENGTH_SHORT)
            }
            else{
                var usermodel = user(binding.loginEmail.editText?.text.toString(), binding.loginPassword.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(usermodel.email!!, usermodel.password!!).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}