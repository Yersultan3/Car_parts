package com.example.car_parts.ui.authorization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.car_parts.R
import com.example.car_parts.models.User
import com.example.car_parts.ui.MainActivity
import com.example.car_parts.ui.authorization.RegistrationActivity.Companion.ARG_PHONE_NUMBER
import com.example.car_parts.ui.authorization.RegistrationActivity.Companion.ARG_TIP
import com.example.car_parts.ui.authorization.RegistrationActivity.Companion.ARG_USER
import com.example.car_parts.viewModels.AuthViewModel
import com.example.car_parts.viewModels.UserViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_authorization.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class Authorization : AppCompatActivity() {

    private lateinit var phoneNumber: String
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var user: User
    //    private val auth by lazy { FirebaseAuth.getInstance() }
//    private val firebaseCloudstore by lazy { FirebaseFirestore.getInstance() }
//    private val users by lazy { firebaseCloudstore.collection(USERS) }
    private val tip by lazy { intent.getStringExtra(ARG_TIP) }
    private val authViewModel: AuthViewModel by viewModel(qualifier = named("authApi"))
    private val userViewModel: UserViewModel by viewModel(qualifier = named("userApi"))
//    private val userDaoViewModel: UserViewModel by viewModel(qualifier = named("userDao"))

//    companion object{
//        private const val USERS = "users"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)


        initUI()
    }

    private fun initUI() {

        authViewModel.snapshot.observe(this, Observer { snapshot ->
            snapshot
                .addOnSuccessListener { document ->
                    if (document.isEmpty){
                        if (tip.isNullOrEmpty()){
                            Toast.makeText(this, "Вы должны зарегистрироваться", Toast.LENGTH_SHORT).show()
                            RegistrationActivity.start(this)
                            finish()
                        }
                        else verifyPhoneNumber()
                    }
                    else {
                        if (tip.isNullOrEmpty()) {
                            verifyPhoneNumber()
                        }
                        else {
                            Toast.makeText(this, "Этот номер уже зарегистрирован", Toast.LENGTH_SHORT).show()
                            RegistrationActivity.start(this)
                            finish()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "yera4" + it.toString(), Toast.LENGTH_LONG).show()
                }
        })

        authViewModel.task.observe(this, Observer {task ->
            task
                .addOnSuccessListener {
                    if(tip != null && tip.isNotEmpty()){
                        user = intent.getParcelableExtra(ARG_USER)!!
                        userViewModel.addUser(user)
                    }
                    else {
                        MainActivity.start(this)
//                        finish()
                    }
                }
                .addOnFailureListener{
                    if(task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Введен непрвильный код подтверждения", Toast.LENGTH_LONG ).show()
                    }
                }

        } )

        userViewModel.response.observe(this, Observer { task ->
            task
                ?.addOnCompleteListener {
                    MainActivity.start(this)
                    finish()
//                    userDaoViewModel.addUser(user)
                }
                ?.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                }
        })

//        userDaoViewModel.response.observe(this, Observer {
//            MainActivity.start(this)
//            finish()
//        })


        phoneNumber = intent.getStringExtra(ARG_PHONE_NUMBER)!!

        sendCode.setOnClickListener {
            sendCode()
        }

        isUserRegistered()
    }

    private fun isUserRegistered(){
        authViewModel.isUserRegistered(phoneNumber)
//        users
//            .whereEqualTo("phoneNumber", phoneNumber)
//            .get()
//            .addOnSuccessListener { documents ->
//                if(documents.isEmpty){
//                    if(tip.isNullOrEmpty()) {
//                        Toast.makeText(this, "Вы должны зарегистрироваться", Toast.LENGTH_LONG).show()
//                        RegistrationActivity.start(this)
//                        finish()
//                    }
//                    else verifyPhoneNumber()
//                }
//                else {
//                    if(tip.isNullOrEmpty()) {
//                        verifyPhoneNumber()
//                    }
//                    else {
//                        Toast.makeText(this, "Этот номер уже зарегистрирован", Toast.LENGTH_LONG).show()
//                        RegistrationActivity.start(this)
//                        finish()
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
//            }
    }

    private fun verifyPhoneNumber(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(applicationContext, "Неправильный номер телефона",
                        Toast.LENGTH_LONG).show()
                }
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, callbacks)
    }

    private fun sendCode(){
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code.text.toString())
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        authViewModel.signInWithPhoneAuthCredential(credential)

//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    if(tip != null && !tip.isNullOrEmpty()){
//                        val user = intent.getParcelableExtra<User>(ARG_USER)!!
//                        users.document(auth.currentUser!!.uid).set(user)
//                            .addOnCompleteListener {
//                                MainActivity.start(this)
//                                finish()
//                            }
//                            .addOnFailureListener {
//                                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
//                            }
//                    }
//                    else {
//                        MainActivity.start(this)
//                        finish()
//                    }
//                } else {
//                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        Toast.makeText(this, "Введен неправильный код подтверждения", Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
    }
}
