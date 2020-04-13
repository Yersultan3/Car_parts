package com.example.car_parts.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.ui.authorization.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

import com.example.car_parts.ui.navFragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class   MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var addFragment: AddFragment
    lateinit var profileFragment: ProfileFragment




    private val auth by lazy { FirebaseAuth.getInstance() }
    private val currentUser by lazy { auth.currentUser }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      supportActionBar?.hide()
        initUI()
        val homeActionbar = supportActionBar
        val profileActionbar = supportActionBar
        val addActionbar = supportActionBar


        R.id.add
        addFragment = AddFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, addFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomBar.onItemSelected = {item->

            when (item) {
                0 -> {
                    R.id.add
                    addFragment = AddFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, addFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
//                    addActionbar!!.title = "Apple Motors"
                }
                1 -> {
                    R.id.home
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
//                    supportActionBar?.hide()
                    homeActionbar!!.title = "Мои товары"



                }
                2 -> {
                    R.id.profile
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                    profileActionbar!!.title = "Профиль"
//                        actionbar.setDisplayHomeAsUpEnabled(true)

                }


            }
//                status.text = "Item $pos selected"
        }

//        var bottomNavigation:BottomNavigationView = findViewById(R.id.btm_nav)
//
//        addFragment = AddFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frame_layout, addFragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()


//        bottomNavigation.setOnNavigationItemSelectedListener{item ->
//            when(item.itemId){
//                R.id.add -> {
//                    addFragment = AddFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, addFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                         addActionbar!!.title = "Apple Motors"
//
//                }
//
//                R.id.home -> {
//                    homeFragment = HomeFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, homeFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                         homeActionbar!!.title = "Мои объявления"
//
////                    HomeFragment.runOnUiThread(java.lang.Runnable{
////                             progressBar_myAd.visibility = View.VISIBLE
////                         }
////                            else {
////                             progressBar_myAd.visibility = View.GONE
////                         }
//
//
//
//                }
//
//                R.id.profile -> {
//                    profileFragment = ProfileFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, profileFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit()
//                         proflieActionbar!!.title = "Профиль"
////                        actionbar.setDisplayHomeAsUpEnabled(true)
//
//                }
//            }
//            true
//        }
    }



    private fun initUI() {
        if(currentUser == null){
            sendToLoginPage()
        }
        else{

        }

//        var bottomNavigation: BottomNavigationView = findViewById(R.id.btm_nav)
//        bottomNavigation.setOnNavigationItemSelectedListener{item ->
//            when(item.itemId){
//                R.id.home -> {
//                    findNavController(HomeFragment())
//                        .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
//
//                }
//
//
//                R.id.profile -> {
//                    findNavController(ProfileFragment())
//                        .navigate(AddFragmentDirections.actionAddFragmentToProfileFragment())
//
//
//                }
////                R.id.profile -> {
////                    findNavController(ProfileFragment())
////                        .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
////                }
//            }
//            true
//        }

    }

    private fun sendToLoginPage() {
        LoginActivity.start(this)
        finish()
    }





}
