package com.example.car_parts.ui.addTires


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.adapter.AddTiresAdapter
import com.example.car_parts.common.Common
import com.example.car_parts.common.SpacesItemDecoration
import com.example.car_parts.models.MyTiresModel
import kotlinx.android.synthetic.main.activity_tires.*

class TiresActivity: AppCompatActivity() {

    lateinit var adapterTires: AddTiresAdapter
    lateinit var tiresItemList: MutableList<MyTiresModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tires)
//        supportActionBar?.hide()
        initTiresData()
        setTiresData()
        val actionbar = supportActionBar
        actionbar!!.title = "Шины"
        actionbar.setDisplayHomeAsUpEnabled(true)
//        actionbar.setDisplayHomeAsUpEnabled(true)


    }



    private fun setTiresData() {
        adapterTires = AddTiresAdapter(this, tiresItemList)

        val layoutManager = GridLayoutManager(this, Common.NUM_OF_COLUMN_TIRES)
        layoutManager.orientation = RecyclerView.VERTICAL
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(i: Int): Int {
                return if(adapterTires != null){
                    when (adapterTires!!.getItemViewType(i)){
                        1 -> 1
                        0 -> Common.NUM_OF_COLUMN_TIRES
                        else -> -1
                    }
                }else{
                    -1
                }
            }
        }
        recycler_view_tires.layoutManager = layoutManager
        recycler_view_tires.addItemDecoration(SpacesItemDecoration(8))
        recycler_view_tires.adapter = adapterTires

    }

    private fun initTiresData() {
        tiresItemList = ArrayList()

        // Items
        tiresItemList.add(MyTiresModel( "Легковые"))
        tiresItemList.add(MyTiresModel( "Коммерческие шины"))
        tiresItemList.add(MyTiresModel( "Грузовые шины"))
        tiresItemList.add(MyTiresModel( "Шины для спецтехники"))
        tiresItemList.add(MyTiresModel( "Шины для картинга"))
        tiresItemList.add(MyTiresModel( "Диски"))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true

    }

}