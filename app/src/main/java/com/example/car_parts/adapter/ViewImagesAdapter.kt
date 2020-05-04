package com.example.car_parts.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.car_parts.R
import com.example.car_parts.models.TireProduct
import com.example.car_parts.ui.homeFragmentItem.TireProductDescriptionActivity
import kotlinx.android.synthetic.main.view_photo.view.*

class ViewImagesAdapter(private val activity: TireProductDescriptionActivity,
                        private val viewDetailsPhotos: List<String>
)
    : RecyclerView.Adapter<ViewImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_photo, parent, false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val intent = Intent()
        val tireProduct = intent.getParcelableExtra<TireProduct>(GetProductsAdapter.ARG_MY_ITEM)!!


        val photo = tireProduct.image
        holder.imageView.load(photo) {
            crossfade(true)
            size(500, 500)
            placeholder(R.drawable.ic_image)
        }
        holder.imageView.setOnClickListener{
            activity.onPhotoClicked(viewDetailsPhotos, position, holder.imageView)
        }

    }

    override fun getItemCount(): Int = viewDetailsPhotos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.viewPhoto

    }

    interface Photo {
        fun  onPhotoClicked (photosList: List<String>, position: Int, target: ImageView)
    }

}

