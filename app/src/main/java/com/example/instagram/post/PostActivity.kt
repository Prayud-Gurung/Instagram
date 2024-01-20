package com.example.instagram.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram.databinding.ActivityPostBinding
import com.example.instagram.models.Post
import com.example.instagram.util.POST_FOLDER
import com.example.instagram.util.POST_NODE
import com.example.instagram.util.USER_PROFILE_FOLDER
import com.example.instagram.util.uploadImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String?=null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let{
            uploadImage(uri, POST_FOLDER){
                url->
                if(url!=null){
                    binding.postImageView.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener{
            finish()
        }

        binding.postImageView.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelPostBtn.setOnClickListener {
            finish()
        }

        binding.postBtn.setOnClickListener {
            val post:Post = Post(imageUrl!!,binding.captionField.editText?.text.toString())

            Firebase.firestore.collection(POST_NODE).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener{
                    finish()
                }
            }
        }
    }
}