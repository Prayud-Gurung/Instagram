package com.example.instagram.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.R
import com.example.instagram.databinding.FragmentAddBinding
import com.example.instagram.post.PostActivity
import com.example.instagram.post.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addPost.setOnClickListener{
            activity?.startActivity(Intent(requireContext(), PostActivity::class.java))
        }

        binding.addReel.setOnClickListener{
            activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))

        }

        return binding.root
    }

    companion object {

    }
}