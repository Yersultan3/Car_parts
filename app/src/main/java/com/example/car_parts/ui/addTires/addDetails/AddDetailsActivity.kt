package com.example.car_parts.ui.addTires.addDetails

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_parts.adapter.AddImagesAdapter
import com.example.car_parts.`interface`.GenerateId.autoId
import com.example.car_parts.R
import com.example.car_parts.`interface`.NetworkManager
import com.example.car_parts.models.TireProduct
import com.example.car_parts.viewModels.TireProductViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class AddDetailsActivity: AppCompatActivity(), View.OnClickListener{

    lateinit var id: String
    lateinit var image: String
//    lateinit var imageList: ArrayList<String>
    lateinit var width: String
    lateinit var profile: String
    lateinit var diameter: String
    lateinit var manufacturer: String
    lateinit var price: String
    private var totalItemsSelected = 0
    lateinit var alertDialog: AlertDialog
    lateinit var addImagesAdapter: AddImagesAdapter
    private var  seasonality = "isNotChecked"
    private var  condition = "isNotChecked"
    private val detailsImages = mutableListOf<Uri>()
    private var filePath: Uri? = null
    private var individualImage: Uri? = null
    private val tireProductViewModel: TireProductViewModel by viewModel(qualifier = named("tireProductApi"))
    val user = FirebaseAuth.getInstance().currentUser
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    internal var imageName: StorageReference? = null


    companion object {
        private val PICK_IMAGE_CODE = 1000

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE &&
            resultCode == Activity.RESULT_OK){
            if (data?.clipData != null){
                totalItemsSelected = data.clipData!!.itemCount
                var currentImagesSelected = 0

                while (currentImagesSelected < totalItemsSelected){
                    filePath = data.clipData!!.getItemAt(currentImagesSelected).uri
                    detailsImages.add(filePath!!)
                    currentImagesSelected += 1
                }
            } else if (data != null && data.data != null){
                    filePath = data.data
                    detailsImages.add(filePath!!)
            }
            detailsPhotos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            addImagesAdapter  = AddImagesAdapter(detailsImages)
            detailsPhotos.adapter = addImagesAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_details)
        val actionbar = supportActionBar
        actionbar!!.title = "Легковые"
        actionbar.subtitle= "Добавить товар"
        actionbar.setDisplayHomeAsUpEnabled(true)

        rdCondition.clearCheck()
        rdSeasonality.clearCheck()
        priceInput.text.clear()

        initUI()

        storage = FirebaseStorage.getInstance()


        id = autoId()
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storageReference = storage?.getReference("tireProducts/${id}")

        val spManufacturers = arrayOf( "Amtel", "Avon", "Barum", "Bridgestone", "Continental", "Cooper", "Cordiant", "Dayton", "Debica", "Dunlop",
            "Falken", "Firestone", "Fulda", "Fuzion", "General Tire", "Gislaved ")

        val width = arrayOf( "7", "7.5", "10.5", "11.5", "30", "31", "32", "33", "35", "37",
            "135", "145", "155", "165", "175", "185 ")

        val profiles = arrayOf( "9.5", "10.5", "12.5", "30", "35", "40",
            "45", "50", "55", "60", "65", "70", "75")

        val diameters = arrayOf( "12", "13", "13C", "14", "14C", "15", "15C", "16", "16C", "17",
            "18", "19", "20", "20C", "21", "22 ")

        spManufacturer.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, spManufacturers )
        spWidth.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, width )
        spProfile.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, profiles )
        spDiameter.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, diameters )

        spManufacturer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "Please Select"
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                result.text = typesOfTires.get(position)
            }
        }

        spWidth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "Please Select"
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                result.text = width.get(position)
            }
        }

        spProfile.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "Please Select"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                result.text = typesOfTires.get(position)
            }
        }

        spDiameter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                result.text = "Please Select"
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                result.text = typesOfTires.get(position)
            }
        }
    }

    private fun initUI() {
        rdSeasonality.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.rdAllSeason -> seasonality = rdAllSeason.text.toString()
                R.id.rdSummer -> seasonality = rdSummer.text.toString()
                R.id.rdWinter -> seasonality = rdWinter.text.toString()
            }
        }
        rdCondition.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.rdNew -> condition = rdNew.text.toString()
                R.id.rdUsed -> condition = rdUsed.text.toString()
            }
        }
        addDetailsBtn.setOnClickListener(this)
        addPhotoBtn.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.addPhotoBtn -> {
                showFileChooser()
            }
            R.id.addDetailsBtn -> {
                addDetails(view)
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.putExtra(SyncStateContract.Constants.INTENT_EXTRA_LIMIT, 10);
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE)

    }

//    private fun storeLink(url: String){
//        databaseReference = FirebaseDatabase.getInstance().reference.child("UserOne")
//        val hashMap = HashMap<String, String>()
//        hashMap["imageLink"] = url
//        databaseReference?.push()?.setValue(hashMap)
//    }


    private fun addDetails(view: View){
        width = spWidth.selectedItem.toString()
        profile = spProfile.selectedItem.toString()
        diameter = spDiameter.selectedItem.toString()
        manufacturer = spManufacturer.selectedItem.toString()
        price = priceInput.text.toString()

        if (width.isEmpty() || profile.isEmpty() || diameter.isEmpty() || price.isEmpty() ||
            manufacturer.isEmpty() || seasonality == "isNotChecked"  ||
            condition == "isNotChecked" )
//                ||  filePath == null
        {
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_LONG).show()
        }
        else if (NetworkManager.isNetworkAvailable(this) || filePath != null){

            alertDialog.setTitle("Uploading")
            alertDialog.show()

            repeat( detailsImages.size) {totalItemsSelected ->
                individualImage = detailsImages[totalItemsSelected]
                imageName = storageReference?.child("" + individualImage?.lastPathSegment)

            val uploadTask = imageName!!.putFile(individualImage!!)
                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful){
                        Toast.makeText(this@AddDetailsActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                    imageName!!.downloadUrl

                }.addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val downloadUrl = task.result
                        image = downloadUrl!!.toString().substring(0,downloadUrl.toString().indexOf("&token"))
//                        imageList = ArrayList(image.length)
//                      storeLink(image)

                        val tireProduct = TireProduct(id, width, profile, diameter, manufacturer, seasonality,
                            condition, image, "$price ₸", user!!.uid)
                        tireProductViewModel.addTireProduct(tireProduct)
                        alertDialog.dismiss()
                        Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            detailsImages.clear()
            addImagesAdapter.notifyDataSetChanged()

            rdCondition.clearCheck()
            rdSeasonality.clearCheck()
            priceInput.text.clear()
        }
        else {
            Snackbar.make(view, "Нет подключения к интернету", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        rdCondition.clearCheck()
        rdSeasonality.clearCheck()
        priceInput.text.clear()
        onBackPressed()
        return true
    }
}