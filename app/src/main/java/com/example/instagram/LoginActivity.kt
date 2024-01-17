package com.example.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.models.user
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            if(binding.loginEmail.editText?.text.toString().equals("") or binding.loginPassword.editText?.text.toString().equals("")){
                Toast.makeText(this@LoginActivity, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                var usermodel = user(binding.loginEmail.editText?.text.toString(), binding.loginPassword.editText?.text.toString())
                Firebase.auth.signInWithEmailAndPassword(usermodel.email!!, usermodel.password!!).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@LoginActivity, "Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}