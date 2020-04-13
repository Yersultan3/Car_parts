package com.example.car_parts.ui.navFragments


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ClipData
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.`interface`.NetworkManager
import com.example.car_parts.adapter.GetProductsAdapter
import com.example.car_parts.models.MyModel
import com.example.car_parts.models.TireProduct
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_details.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.my_ad_items.*
import java.text.FieldPosition
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(){



    lateinit var adapter: GetProductsAdapter
    //    lateinit var image: String
    private val itemList = mutableListOf<TireProduct>()
    //     lateinit var itemList2: MutableList<TireProduct>
    lateinit var recycler_view_myAd: RecyclerView
//    lateinit var storageReference: StorageReference

//    lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recycler_view_myAd = root.findViewById(R.id.recycler_view_myAd)
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (NetworkManager.isNetworkAvailable(requireContext())){


        }
        else
        {
            Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG).show()
        }

        initData()
//        fun setElevationVisibility(show: Boolean) {}


    }



    private fun setData() {
        if (context != null) {
            adapter = GetProductsAdapter(activity, this, itemList)
            recycler_view_myAd.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recycler_view_myAd.adapter = adapter
        }
    }

    private fun initData() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        db.collection("tireProducts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("addedBy").toString() == user?.uid) {
                        itemList.add(TireProduct(
                            document.get("id").toString(),
                            document.get("width").toString(),
                            document.get("profile").toString(),
                            document.get("diameter").toString(),
                            document.get("manufacturer").toString(),
                            document.get("seasonality").toString(),
                            document.get("condition").toString(),
                            document.get("image").toString(),
                            document.get("price").toString(),
                            document.get("addedBy").toString()
                        ))

                    }
                }
                setData()
            }
            .addOnFailureListener { exception ->
                Log.d("error", "Error getting documents: ", exception)
            }
    }

    fun deleteItem(position: Int) {
        itemList.removeAt(position)
        adapter.notifyDataSetChanged()
    }


}