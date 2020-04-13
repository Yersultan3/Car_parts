package com.example.car_parts.ui.authorization

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NavUtils
import com.example.car_parts.R
import com.example.car_parts.`interface`.NetworkManager
import com.example.car_parts.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var address: String
//    private lateinit var password: String
//    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }


    companion object{
        const val ARG_USER = "user"
        const val ARG_TIP = "tip"
        const val ARG_PHONE_NUMBER = "phoneNumber"

        fun start(context: Context){
            context.startActivity(Intent(context, RegistrationActivity::class.java))
        }

        fun getPhoneNumber(phoneNum: String): String {
            var phoneNumber = phoneNum
            if(phoneNumber[0] == '8' && phoneNumber.length == 11) {
                phoneNumber = "+7" + phoneNumber.substring(1)
            }

            return phoneNumber
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        initUI()

    }

    private fun initUI() {
        sighUp_btn.setOnClickListener(this)
        goToSignIn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.sighUp_btn -> {
                signUp(view)
            }
            R.id.goToSignIn -> {
                LoginActivity.start(this)
            }
        }
    }

    private fun signUp(view: View){
        name = nameInput.text.toString()
        address = addressInput.text.toString()
//        password = passwordInput.text.toString()
//        phoneNumber = phoneNumberInput.text.toString()
        phoneNumber = phoneNumberInput.text.toString()
//
//        val savedPhoneNumber = PreferenceUtils.getEmail(this)


        if(name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()){
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_LONG).show()
        }

        else if (NetworkManager.isNetworkAvailable(this))
        {

            phoneNumber = getPhoneNumber(phoneNumberInput.text.toString())
            val tip = "registration"
            val user = User(name, phoneNumber, address)
            val intent = Intent(this, Authorization::class.java)
            intent.putExtra(ARG_USER, user)
            intent.putExtra(ARG_TIP, tip)
            intent.putExtra(ARG_PHONE_NUMBER, phoneNumber)
            startActivity(intent)
        }else{
            Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG).show()
        }
    }

}
