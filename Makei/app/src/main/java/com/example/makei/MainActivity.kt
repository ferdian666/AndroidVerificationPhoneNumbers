package com.example.makei

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.makei.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var mCallbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        binding.btnVerification.setOnClickListener(){
            startPhonenumber()
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG","onVerificatinCompleted$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("TAG","onVerificationFailed", e );
                if (e is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this@MainActivity,"errors",Toast.LENGTH_SHORT).show();
                }
                else if (e is FirebaseTooManyRequestsException)
                {
                    Toast.makeText(this@MainActivity,"tomanyArgument",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {


    }

    private fun startPhonenumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            binding.numberphone.text.toString(),
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks);
    }
}