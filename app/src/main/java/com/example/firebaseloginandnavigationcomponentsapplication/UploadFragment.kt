package com.example.firebaseloginandnavigationcomponentsapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.firebaseloginandnavigationcomponentsapplication.databinding.FragmentUploadBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

const val REQUEST_CODE_UPLOAD =0
class UploadFragment :Fragment(R.layout.fragment_upload) {

    lateinit var binding: FragmentUploadBinding
    var pathRef = Firebase.storage.reference
     var image_uri:Uri? =null
     var image_name:String? =null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadBinding.bind(view)



        binding.ivUpload.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_UPLOAD)
            }
        }

        binding.btnUpload.setOnClickListener {
            uploadImage(image_name!!)
        }
    }


    private fun uploadImage(fileName:String) = CoroutineScope(Dispatchers.IO).launch {

        try{
            image_uri?.let {
                pathRef.child("Images/$fileName").putFile(it).await()
            }
        }catch (e:Exception){

        }

    }








    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_UPLOAD){
            data?.data?.let {
                image_uri = it
                image_name =it.lastPathSegment
                binding.ivUpload.setImageURI(it)

            }
        }


    }
}