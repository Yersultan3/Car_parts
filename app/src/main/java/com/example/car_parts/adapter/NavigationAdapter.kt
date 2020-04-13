//package com.example.car_parts.adapter
//
//import android.content.Context
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import com.example.car_parts.ui.fragments.AddFragment
//import com.example.car_parts.ui.fragments.HomeFragment
//import com.example.car_parts.ui.fragments.ProfileFragment
//
//class NavigationAdapter(private val myContext: Context, fm: FragmentManager) :
//    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//    override fun getItem(position: Int): Fragment {
//        return when(position) {
//            0 -> AddFragment()
//            1 -> HomeFragment()
//            2 -> ProfileFragment()
//            else -> Fragment()
//        }
//    }
//
//    override fun getCount(): Int {
//        return 3
//    }
//
//
//}