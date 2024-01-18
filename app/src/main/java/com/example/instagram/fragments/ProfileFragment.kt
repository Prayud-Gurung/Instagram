package com.example.instagram.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.instagram.SignUpActivity
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.models.user
import com.example.instagram.util.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding :FragmentProfileBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfileBtn.setOnClickListener {
            var intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

    companion object {


    }

    override fun onStart() {
        super.onStart()

        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val usermodel:user= it.toObject<user>()!!
            binding.nameTextView.text = usermodel.name
            binding.bioTextView.text = usermodel.email
            if(!usermodel.image.isNullOrEmpty()){
                Picasso.get().load(usermodel.image).into(binding.profileImage)
            }
        }
    }
}