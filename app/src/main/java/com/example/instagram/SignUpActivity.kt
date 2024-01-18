package com.example.instagram

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.databinding.ActivitySignUpBinding
import com.example.instagram.models.user
import com.example.instagram.util.USER_NODE
import com.example.instagram.util.USER_PROFILE_FOLDER
import com.example.instagram.util.uploadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    lateinit var usermodel:user
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it==null){

                }else{
                    usermodel.image = it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        usermodel = user()

        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE", -1)==1){
                binding.registerBtn.text = "Update Profile"

                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    usermodel= it.toObject<user>()!!

                    if(!usermodel.image.isNullOrEmpty()){
                        Picasso.get().load(usermodel.image).into(binding.profileImage)
                    }
                }
            }

        }

        binding.registerBtn.setOnClickListener {
            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE", -1) == 1){

                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(usermodel).addOnSuccessListener {
                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
            else{
                if(binding.nameField.editText?.text.toString().equals("") or binding.emailField.editText?.text.toString().equals("") or binding.passwordField.editText?.text.toString().equals("")){
                    Toast.makeText(this@SignUpActivity, "Please fill fields", Toast.LENGTH_LONG).show()
                }
                else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailField.editText?.text.toString(), binding.passwordField.editText?.text.toString()).addOnCompleteListener {
                            result->
                        if(result.isSuccessful){

                            usermodel.name = binding.nameField.editText?.text.toString()
                            usermodel.email = binding.emailField.editText?.text.toString()
                            usermodel.password = binding.passwordField.editText?.text.toString()

                            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(usermodel).addOnSuccessListener {
                                startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                                finish()
                            }
                        }
                        else{
                            Toast.makeText(this@SignUpActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

        binding.profilePlus.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.loginTextView.setOnClickListener{
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}