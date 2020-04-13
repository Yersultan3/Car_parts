package com.example.car_parts.ui.navFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.car_parts.R
import com.example.car_parts.ui.authorization.LoginActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    var myAuth = FirebaseAuth.getInstance()
    lateinit var logoutBtn : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutBtn = view.findViewById(R.id.logout_btn)
        logoutBtn.setOnClickListener{
            Toast.makeText(context, "Logging out ", Toast.LENGTH_LONG).show()
            signOut()
        }
    }

    fun signOut() {
        myAuth.signOut()
        myAuth.addAuthStateListener {
            if (myAuth.currentUser == null){
                sendToLoginPage()
            }
        }
    }
    private fun sendToLoginPage() {
//        LoginActivity.start(requireCont ext())
        LoginActivity.start(context!!)
    }

}
