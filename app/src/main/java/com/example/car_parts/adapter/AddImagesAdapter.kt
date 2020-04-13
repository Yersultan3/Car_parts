package com.example.bcompanionkotlin.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.car_parts.R
import com.example.car_parts.ui.addTires.addDetails.AddDetailsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_photo.view.*

class AddImagesAdapter(private val detailsPhotos: List<Uri>)
    : RecyclerView.Adapter<AddImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = detailsPhotos[position]
        holder.imageView.load(photo) {
            crossfade(true)
            size(500, 500)
            placeholder(R.drawable.ic_image)
        }    }

    override fun getItemCount(): Int = detailsPhotos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.detailPhoto
    }

}

