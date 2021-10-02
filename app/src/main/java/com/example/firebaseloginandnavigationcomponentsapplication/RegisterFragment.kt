package com.example.firebaseloginandnavigationcomponentsapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseloginandnavigationcomponentsapplication.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegisterFragment :Fragment(R.layout.fragment_register) {
    lateinit var binding: FragmentRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            register()
        }

    }
    private fun register(){
        var email =binding.etRegisterEmail.text.toString()
        var password =binding.etRegisterPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch{
            try {
                auth.createUserWithEmailAndPassword(email,password).await()
                withContext(Dispatchers.Main){
                    checkLogin()
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(activity,"Error in Register",Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    private fun checkLogin(){
        var user =auth.currentUser
        if(user == null){
            Toast.makeText(activity,"user Not Register",Toast.LENGTH_LONG).show()
        }else{
            findNavController().navigate(R.id.action_registerFragment_to_contentFragment)
        }

    }


}