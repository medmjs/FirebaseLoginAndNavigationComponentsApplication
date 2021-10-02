package com.example.firebaseloginandnavigationcomponentsapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseloginandnavigationcomponentsapplication.databinding.FragmentContentBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ContentFragment : Fragment(R.layout.fragment_content) {
    lateinit var binding: FragmentContentBinding
    val pathRef = Firebase.storage.reference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContentBinding.bind(view)

        binding.tvContentUpload.setOnClickListener {
            findNavController().navigate(R.id.action_contentFragment_to_uploadFragment)
        }

        binding.tvContentDelete.setOnClickListener {
            delete()
        }

        getAllUrl()


    }


    fun getAllUrl() = CoroutineScope(Dispatchers.IO).launch {
        try {
        val images = pathRef.child("Images/").listAll().await()
        val urls = mutableListOf<String>()

        for (image in images.items) {
            val url = image.downloadUrl.await()
            urls.add(url.toString())
        }

        withContext(Dispatchers.Main){
            var urlAdapter = ImageAdapter(urls)
            binding.rvContent.apply {
               layoutManager = LinearLayoutManager(activity)
                adapter =urlAdapter

            }
        }
        }catch (e:java.lang.Exception){
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Error in Call From Firebase", Toast.LENGTH_LONG).show()
            }
        }


    }


    private fun delete() {
        var name = binding.etContentNameDelete.text.toString()
        if (name.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    pathRef.child("Images/$name").delete().await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Successfully Delete ", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Error in Delete", Toast.LENGTH_LONG).show()
                    }
                }


            }

        }
    }

}