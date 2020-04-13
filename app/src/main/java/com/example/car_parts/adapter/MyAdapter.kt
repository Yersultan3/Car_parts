package com.example.car_parts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.`interface`.ICardItemClickListener
import com.example.car_parts.common.Common
import com.example.car_parts.models.MyModel
import com.example.car_parts.ui.addTires.TiresActivity


class MyAdapter(internal var context: Context,
                internal var myItems: List<MyModel>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val  itemView = LayoutInflater.from(context)
            .inflate(R.layout.layout_card_square, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.img_icon.setImageResource(myItems[p1].icon)
        p0.txt_description.text = myItems[p1].description

        p0.setEvent(object:ICardItemClickListener{
            override fun onCartItemClick(view: View, position: Int){
//                Toast.makeText(context, "Clicked " + myItems.get(position).description, Toast.LENGTH_LONG).show()

                when(position) {
                    0 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    1 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    2 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    3 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    4 -> context.startActivity(Intent(context, TiresActivity::class.java))
//                    5 -> context.startActivity(Intent(context, TiresActivity::class.java))


                }
            }
        })
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{

        internal var img_icon: ImageView
        internal var txt_description: TextView
        internal lateinit var iCardItemClickListener: ICardItemClickListener

        fun setEvent(iCardItemClickListener:ICardItemClickListener){
            this.iCardItemClickListener = iCardItemClickListener
        }

        init {
            img_icon = itemView.findViewById<View>(R.id.image_icon) as ImageView
            txt_description = itemView.findViewById<View>(R.id.txt_description) as TextView

            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            iCardItemClickListener.onCartItemClick(p0!!, adapterPosition)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if(myItems.size == 1)
            0 // If item size is 1, just return 1 column (full width)
        else{
            if (myItems.size % Common.NUM_OF_COLUMN == 0 ) //if size div for  column num == 0. just return default column num
                1
            else
                if(position > 1 && position == myItems.size - 1) 0 else 1

        }
    }

}