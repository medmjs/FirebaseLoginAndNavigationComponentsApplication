package com.example.firebaseloginandnavigationcomponentsapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseloginandnavigationcomponentsapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        auth.signOut()


        binding.btnLogin.setOnClickListener {
            login()
            //  findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvLoginRgister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login() {
        var userName = binding.etLoginEmail.text.toString()
        var password = binding.etLoginPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(userName, password).await()
                withContext(Dispatchers.Main) {
                   checkLogin()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Error In Login", Toast.LENGTH_LONG).show()
                    binding.tvLoginResult.text = "You Don't have account "
                    binding.tvLoginRgister.text = "Sign Up "
                }
            }

        }

    }

    private fun checkLogin(){
        var user = auth.currentUser
        if(user == null){
            binding.tvLoginResult.text = "You Don't have account "
            binding.tvLoginRgister.text = "Sign Up "
        }else{
            findNavController().navigate(R.id.action_loginFragment_to_contentFragment)
        }
    }


}