package com.example.car_parts.ui.homeFragmentItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.car_parts.adapter.ViewImagesAdapter
import com.example.car_parts.R
import com.example.car_parts.adapter.GetProductsAdapter
import com.example.car_parts.models.TireProduct
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_tire_product_description.*
import kotlinx.android.synthetic.main.view_photo.*

class TireProductDescriptionActivity : AppCompatActivity(), ViewImagesAdapter.Photo  {


        private lateinit var viewer: StfalconImageViewer<String>
        private lateinit var tireProduct: TireProduct
        private val imageList = mutableListOf<String>()
        private lateinit var viewPhotosAdapter: ViewImagesAdapter
        private val viewPool by lazy { RecyclerView.RecycledViewPool() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tire_product_description)
//        supportActionBar?.hide()
        val actionbar = supportActionBar
        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(true)

        tireProduct = intent.getParcelableExtra<TireProduct>(GetProductsAdapter.ARG_MY_ITEM)!!

        priceDesc.text = tireProduct.price
        conditionDesc.text = tireProduct.condition


        tireProductImage.load(tireProduct.image){
            crossfade(750)
            size(2500, 1500)
//            scale(Scale.FILL)
            placeholder(R.drawable.ic_image)
        }


        viewPhotosAdapter = ViewImagesAdapter(this, imageList)
        recycler_view_viewImages.adapter = viewPhotosAdapter
        recycler_view_viewImages.setRecycledViewPool(viewPool)

    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }

    override fun onPhotoClicked(
        photosList: List<String>, position: Int, target: ImageView) {
        viewer = StfalconImageViewer.Builder(this, photosList){ view, image ->


//            view.load(image){
//                crossfade(750)
//                size(2500, 1500)
////            scale(Scale.FILL)
//                placeholder(R.drawable.ic_image)
//            }

//            Picasso.get().load(image).into(view)

        }
            .withStartPosition(position)
            .withTransitionFrom(target)
            .withHiddenStatusBar(false)
            .show()
    }


//    override fun onPhotoClicked(photosList: List<String>, position: Int, target: ImageView) {
//        viewer = StfalconImageViewer.Builder(this, photosList){ view, _ ->
//
//
//            view.load(tireProduct.image){
//                crossfade(750)
//                size(2500, 1500)
////            scale(Scale.FILL)
//                placeholder(R.drawable.ic_image)
//            }
//
////            Picasso.get().load(image).into(view)
//
//        }
//            .withStartPosition(position)
//            .withTransitionFrom(target)
//            .withHiddenStatusBar(false)
//            .show()
//
//    }
}
