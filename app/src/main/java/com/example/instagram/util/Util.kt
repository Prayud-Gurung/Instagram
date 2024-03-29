package com.example.instagram.util

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID
import javax.security.auth.callback.Callback

fun uploadImage(uri:Uri, folderName:String, callback:(String?)->Unit) {
    var imageUrl:String?=null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri).addOnSuccessListener{
        it.storage.downloadUrl.addOnSuccessListener {
            imageUrl = it.toString();
            callback(imageUrl)
        }
    }
}