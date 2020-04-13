package com.example.car_parts.ui.authorization

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.car_parts.R
import com.example.car_parts.`interface`.NetworkManager
import com.example.car_parts.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var phoneNumber: String
//    private lateinit var password: String

//    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    companion object{
        const val ARG_PHONE_NUMBER = "phoneNumber"

        fun start(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initUI()
    }

    private fun initUI() {
        signIn.setOnClickListener(this)
        goToSignUp.setOnClickListener(this)
    }



    override fun onClick(view: View) {
        when(view.id){
            R.id.signIn -> {
                signIn(view)
            }
            R.id.goToSignUp -> {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            }
        }
    }



    private fun signIn(view: View){

//        phoneNumber = RegistrationActivity.getPhoneNumber(phoneNumberInput.text.toString())
        phoneNumber = phoneNumberInput.text.toString()

//        password = login_password.text.toString()
        if(phoneNumber.isNullOrEmpty()){
//            Toast.makeText(this, "Введите номер телефона ", Toast.LENGTH_LONG).show()
            Snackbar.make(view, "Введите номер телефона", Snackbar.LENGTH_LONG).show()
        }
        else if (NetworkManager.isNetworkAvailable(this)){
//            firebaseAuth.signInWithEmailAndPassword(phoneNumber, password).addOnCompleteListener {
//                    task ->
//                run {
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "Success",
//                            Toast.LENGTH_LONG).show()
//                        MainActivity.start(this)
//                        finish()
//                    } else {
//                        val error = task.exception
//
//                        Toast.makeText(this, error?.message,
//                            Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
            val intent = Intent(this, Authorization::class.java)
            phoneNumber = RegistrationActivity.getPhoneNumber(phoneNumberInput.text.toString())
            intent.putExtra(ARG_PHONE_NUMBER, phoneNumber)
            startActivity(intent)
        }
        else{
            Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG).show()
//            val intent = Intent(this, Authorization::class.java)
//            phoneNumber = RegistrationActivity.getPhoneNumber(phoneNumberInput.text.toString())
//            intent.putExtra(ARG_PHONE_NUMBER, phoneNumber)
//            startActivity(intent)
        }
    }
}
