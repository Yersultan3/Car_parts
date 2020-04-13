package com.example.car_parts.ui.homeFragmentItem

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.Coil
import coil.api.get
import coil.api.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.car_parts.R
import com.example.car_parts.adapter.GetProductsAdapter
import com.example.car_parts.models.MyModel
import com.example.car_parts.models.TireProduct
import com.squareup.picasso.Picasso
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_tire_product_description.*

class TireProductDescriptionActivity : AppCompatActivity() {

//      lateinit var image: MutableList<TireProduct>
        private val image = mutableListOf<TireProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tire_product_description)
//        supportActionBar?.hide()
        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)




        val tireProduct = intent.getParcelableExtra<TireProduct>(GetProductsAdapter.ARG_MY_ITEM)!!

        priceDesc.text = tireProduct.price
        conditionDesc.text = tireProduct.condition


        tireProductImage.load(tireProduct.image){
            crossfade(750)
            size(2500, 1500)
//            scale(Scale.FILL)
            placeholder(R.drawable.ic_image)
        }



//        StfalconImageViewer.Builder<Image>(this, tireProduct.image) { view, image ->
//            Picasso.get().load().into(view)
//        }.show()
    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }
}
