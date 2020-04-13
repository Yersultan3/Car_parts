package com.example.car_parts.ui.navFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.R
import com.example.car_parts.adapter.MyAdapter
import com.example.car_parts.common.Common
import com.example.car_parts.common.SpacesItemDecoration
import com.example.car_parts.models.MyModel
import kotlinx.android.synthetic.main.fragment_add.*

/**
 * A simple [Fragment] subclass.
 */
class AddFragment : Fragment()  {
//    Fragment()
//
        lateinit var adapter: MyAdapter
        lateinit var itemList: MutableList<MyModel>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_add)
//
//        initData()
//        setData()
//
//    }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_add, container, false)


//            setData()

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setData()

    }


    private fun setData() {
        adapter = MyAdapter(context!!, itemList)

        val layoutManager = GridLayoutManager(context, Common.NUM_OF_COLUMN)
        layoutManager.orientation = RecyclerView.VERTICAL
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(i: Int): Int {
                return if(adapter != null){
                    when (adapter!!.getItemViewType(i)){
                        1 -> 1
                        0 -> Common.NUM_OF_COLUMN
                        else -> -1
                    }
                }else{
                    -1
                }
            }
        }
        recycler_view.layoutManager = layoutManager
        recycler_view.addItemDecoration(SpacesItemDecoration(8))
        recycler_view.adapter =adapter

    }

    private fun initData() {
        itemList = ArrayList()

        // Items
        itemList.add(MyModel(R.drawable.tires, "Шины"))
        itemList.add(MyModel(R.drawable.tires, "Диски"))
        itemList.add(MyModel(R.drawable.tires, "Шины"))
        itemList.add(MyModel(R.drawable.tires, "Шины"))
        itemList.add(MyModel(R.drawable.tires, "Шины"))
        itemList.add(MyModel(R.drawable.tires, "Шины"))



//        onCartItemClick.setOnClickLis
}

}
