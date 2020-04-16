package com.example.car_parts.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.car_parts.R
import com.example.car_parts.`interface`.GenerateId
import com.example.car_parts.ui.homeFragmentItem.TireProductDescriptionActivity
import com.example.car_parts.`interface`.ICardItemClickListener
import com.example.car_parts.common.Common
import com.example.car_parts.models.TireProduct
import com.example.car_parts.ui.navFragments.HomeFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tire_product_description.*
import kotlinx.android.synthetic.main.my_ad_items.view.*
import kotlin.math.absoluteValue


class GetProductsAdapter(private val activity: FragmentActivity?, private val fragment: HomeFragment?,
                         private val myProducts: List<TireProduct>)
    :RecyclerView.Adapter<GetProductsAdapter.MyViewHolder>() {

    companion object{
        const val ARG_MY_ITEM = "myItem"
    }

    lateinit var id: String

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.price.text = myProducts[position].price
        holder.manufacturer.text = myProducts[position].manufacturer
        holder.seasonality.text = myProducts[position].seasonality
        holder.condition.text = myProducts[position].condition



        val image = myProducts[position].image

        holder.image.load(image){
            crossfade(750)
            size(500, 300)
            placeholder(R.drawable.ic_image)
        }

        holder.itemView.setOnClickListener{
            sendToMyAdItem(myProducts[position])
        }




        val db = FirebaseFirestore.getInstance()
        itemView.textViewOptions.setOnClickListener { add ->
            val popup = PopupMenu(itemView.context, add)
            Log.d("deleteMenu", popup.toString())
            popup.inflate(R.menu.item_menu_myad)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.delete_item ->
                        db.collection("tireProducts").document(myProducts[position].id)
                            .delete()
                            .addOnSuccessListener {
                                fragment?.deleteItem(position)
                                Log.d("success", "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w("error", "Error deleting document", e) }
                }
                true
            }
            popup.show()
        }
    }

    private fun sendToMyAdItem(myItem: TireProduct){
        Log.d("argyn", activity.toString())
        val intent = Intent(activity, TireProductDescriptionActivity::class.java)
        intent.putExtra(ARG_MY_ITEM, myItem)
        activity?.startActivity(intent)
    }

    lateinit var itemView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_ad_items, parent, false)
//            itemView.setOnClickListener{item ->
//                sendToMyAdItem(item.toString())
//                Log.d("onCLicked", item.toString())
//
//            }
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return myProducts.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{

        //        internal var img_icon: ImageView
        val price: TextView = itemView.findViewById<View>(R.id.text_view_price) as TextView
        val manufacturer: TextView = itemView.findViewById<View>(R.id.text_view_manufacturer) as TextView
        val seasonality: TextView = itemView.text_view_seasonality
        val condition: TextView = itemView.findViewById<View>(R.id.text_view_condition) as TextView
        val image: ImageView = itemView.imageViewMyAd


        internal lateinit var iCardItemClickListener: ICardItemClickListener

        //        fun setEvent(iCardItemClickListener:ICardItemClickListener){
//            this.iCardItemClickListener = iCardItemClickListener
//        }
////
        override fun onClick(p0: View?) {
            iCardItemClickListener.onCartItemClick(p0!!, adapterPosition)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(myProducts.size == 1)
            0 // If item size is 1, just return 1 column (full width)
        else{
            if (myProducts.size % Common.NUM_OF_COLUMN == 0 ) //if size div for  column num == 0. just return default column num
                1
            else
                if(position > 1 && position == myProducts.size - 1) 0 else 1
        }
    }
}