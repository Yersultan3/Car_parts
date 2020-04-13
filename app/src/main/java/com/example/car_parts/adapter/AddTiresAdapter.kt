package com.example.car_parts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.`interface`.ICardItemClickListener
import com.example.car_parts.common.Common
import com.example.car_parts.models.MyTiresModel
import com.example.car_parts.ui.addTires.addDetails.AddDetailsActivity

class AddTiresAdapter(internal var context: Context,
                internal var myItemsTires: List<MyTiresModel>):
    RecyclerView.Adapter<AddTiresAdapter.AddTiresViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AddTiresViewHolder {
        val  itemView = LayoutInflater.from(context)
            .inflate(R.layout.layout_tires_card_square, p0, false)
        return AddTiresViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myItemsTires.size
    }

    override fun onBindViewHolder(p0: AddTiresViewHolder, p1: Int) {
//        p0.img_icon.setImageResource(myItems[p1].icon)
        p0.txt_description_tires.text = myItemsTires[p1].tiresdescription

        p0.setEvent(object:ICardItemClickListener{
            override fun onCartItemClick(view: View, position: Int){
//                Toast.makeText(context, "Clicked " + myItems.get(position).description, Toast.LENGTH_LONG).show()

                when(position) {
                    0 -> context.startActivity(Intent(context, AddDetailsActivity::class.java))
//                    1 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    2 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    3 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    4 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    5 -> context.startActivity(Intent(context, TiresActivity::class.java))
//
//
                }
            }
        })
    }

    class AddTiresViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{

//        internal var img_icon: ImageView
        internal var txt_description_tires: TextView
        internal lateinit var iCardItemClickListener: ICardItemClickListener

        fun setEvent(iCardItemClickListener:ICardItemClickListener){
            this.iCardItemClickListener = iCardItemClickListener
        }

        init {
//            img_icon = itemView.findViewById<View>(R.id.image_icon) as ImageView
            txt_description_tires = itemView.findViewById<View>(R.id.txt_description_tires) as TextView

            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            iCardItemClickListener.onCartItemClick(p0!!, adapterPosition)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if(myItemsTires.size == 1)
            0 // If item size is 1, just return 1 column (full width)
        else{
            if (myItemsTires.size % Common.NUM_OF_COLUMN_TIRES == 0 ) //if size div for  column num == 0. just return default column num
                1
            else
                if(position > 1 && position == myItemsTires.size - 1) 0 else 1

        }
    }

}