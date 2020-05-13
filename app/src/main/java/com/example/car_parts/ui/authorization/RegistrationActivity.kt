package com.example.car_parts.ui.authorization

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.car_parts.R
import com.example.car_parts.`interface`.NetworkManager
import com.example.car_parts.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.plugins.places.picker.PlacePicker
import com.mapbox.mapboxsdk.plugins.places.picker.model.PlacePickerOptions
import kotlinx.android.synthetic.main.activity_registration.*
import org.imperiumlabs.geofirestore.GeoFirestore

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var name: String
    private lateinit var phoneNumber: String
    private var latitude = Double.MAX_VALUE
    private var longitude = Double.MAX_VALUE
    private  var carmenFeature: CarmenFeature? = null
    private var selectedLocationTextView: TextView? = null




//    private lateinit var password: String
//    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }


    companion object{
        const val ARG_USER = "user"
        const val ARG_TIP = "tip"
        const val ARG_PHONE_NUMBER = "phoneNumber"
        private val PLACE_SELECTION_REQUEST_CODE = 56789


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
        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_registration)

        selectedLocationTextView = findViewById(R.id.addressInput)
        supportActionBar?.hide()

        initUI()




//        val imageView: ImageView = selectMapImage
    }

     private fun initUI() {
        sighUp_btn.setOnClickListener(this)
        goToSignIn.setOnClickListener(this)
        selectMapImage.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.sighUp_btn -> {
                signUp(view)
            }
            R.id.goToSignIn -> {
                LoginActivity.start(this)
            }
            R.id.selectMapImage -> {
               goToPLacePicker()
            }
        }
    }

    val collectionRef = FirebaseFirestore.getInstance().collection("users")
    val geoFirestore = GeoFirestore(collectionRef)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_SELECTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Retrieve the information from the selected location's CarmenFeature

             carmenFeature = PlacePicker.getPlace(data)


            if (carmenFeature != null) {
                selectedLocationTextView?.text = String.format(
                    getString(R.string.selected_place_info), carmenFeature!!.center()!!.coordinates()
                )
            }
        }
    }

    private fun goToPLacePicker(){



        val intent = PlacePicker.IntentBuilder()
            .accessToken(Mapbox.getAccessToken()!!)
            .placeOptions(
                PlacePickerOptions.builder()
                    .statingCameraPosition(
                        CameraPosition.Builder()
                            .target(LatLng(43.25654, 76.92848))
                            .zoom(16.0)
                            .build())
                    .build())
            .build(this)
        startActivityForResult(intent, PLACE_SELECTION_REQUEST_CODE)
    }

    private fun signUp(view: View){
        name = nameInput.text.toString()
        phoneNumber = phoneNumberInput.text.toString()
//        val savedPhoneNumber = PreferenceUtils.getEmail(this)

        if(name.isEmpty() || phoneNumber.isEmpty() ){
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_LONG).show()
        }
        else if (NetworkManager.isNetworkAvailable(this))
        {
             phoneNumber= getPhoneNumber(phoneNumberInput.text.toString())
             latitude = carmenFeature?.center()!!.latitude()
             longitude = carmenFeature?.center()!!.longitude()

            val tip = "registration"
            val user = User(name, phoneNumber, longitude, latitude)
            val intent = Intent(this, Authorization::class.java)
            intent.putExtra(ARG_USER, user)
            intent.putExtra(ARG_TIP, tip)
            intent.putExtra(ARG_PHONE_NUMBER, phoneNumber)
            startActivity(intent)
        }
        else{
            Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG).show()
        }
    }

}
