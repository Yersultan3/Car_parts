package com.example.car_parts.ui.addTires.addDetails

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bcompanionkotlin.adapters.AddImagesAdapter
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
import kotlinx.android.synthetic.main.detail_photo.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*

class AddDetailsActivity: AppCompatActivity(), View.OnClickListener{

    lateinit var id: String
//    lateinit var image: List<ImagesDeatails>
    lateinit var image: String
    lateinit var width: String
    lateinit var profile: String
    lateinit var diameter: String
    lateinit var manufacturer: String
    private var  seasonality = "isNotChecked"
    private var  condition = "isNotChecked"
    lateinit var price: String
    lateinit var alertDialog: AlertDialog
    lateinit var addImagesAdapter: AddImagesAdapter
    private val detailsImages = mutableListOf<Uri>()
    val user = FirebaseAuth.getInstance().currentUser
    private var filePath: Uri? = null
    internal var storage: FirebaseStorage? = null
    internal var storageReference: StorageReference? = null
    private val tireProductViewModel: TireProductViewModel by viewModel(qualifier = named("tireProductApi"))

    companion object {
        private val PICK_IMAGE_CODE = 1000

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE &&
            resultCode == Activity.RESULT_OK){
            if (data?.clipData != null){
                val totalItemsSelected = data.clipData!!.itemCount
                repeat(totalItemsSelected) {
                    filePath = data.clipData!!.getItemAt(it).uri
                    detailsImages.add(filePath!!)


                }
                detailsPhotos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
                addImagesAdapter  = AddImagesAdapter(detailsImages)
                detailsPhotos.adapter = addImagesAdapter

            }
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
//        storageReference = storage!!.reference


        id = autoId()
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storageReference = FirebaseStorage.getInstance().getReference("tireProduct_images/${id}/" + UUID.randomUUID())
//
//        addPhotoBtn.setOnClickListener{



//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE)
//        }

        val spManufacturers = arrayOf( "Amtel", "Avon", "Barum", "Bridgestone", "Continental", "Cooper", "Cordiant", "Dayton", "Debica", "Dunlop",
            "Falken", "Firestone", "Fulda", "Fuzion", "General Tire", "Gislaved ")

        val width = arrayOf( "7", "7.5", "10.5", "11.5", "30", "31", "32", "33", "35", "37",
            "135", "145", "155", "165", "175", "185 ")

        val profiles = arrayOf( "9.5", "10.5", "12.5", "30", "35", "40",
            "45", "50", "55", "60", "65", "70", "75")

        val diameters = arrayOf( "12", "13", "13C", "14", "14C", "15", "15C", "16", "16C", "17",
            "18", "19", "20", "20C", "21", "22 ")

        spManufacturer.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spManufacturers )
        spWidth.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, width )
        spProfile.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, profiles )
        spDiameter.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diameters )

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
        rdSeasonality.setOnCheckedChangeListener { group, checkedId ->
            Log.d("yera", "here")
            when(checkedId) {
                R.id.rdAllSeason -> seasonality = rdAllSeason.text.toString()
                R.id.rdSummer -> seasonality = rdSummer.text.toString()
                R.id.rdWinter -> seasonality = rdWinter.text.toString()
            }
        }
        rdCondition.setOnCheckedChangeListener { group, checkedId ->
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
//
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_CODE)

    }





    private fun addDetails(view: View){
        width = spWidth.selectedItem.toString()
        profile = spProfile.selectedItem.toString()
        diameter = spDiameter.selectedItem.toString()
        manufacturer = spManufacturer.selectedItem.toString()
        price = priceInput.text.toString() + " ₸"

        if (width.isEmpty() || profile.isEmpty() || diameter.isEmpty() ||
            manufacturer.isEmpty() || seasonality == "isNotChecked"  ||
            condition == "isNotChecked" || price.isEmpty() || filePath == null)
        {
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_LONG).show()
        }
        else if (NetworkManager.isNetworkAvailable(this) || filePath != null){


//                val progressDialog = ProgressDialog(this)
//                progressDialog.setTitle("Uploading...")
//                progressDialog.show()
//
//                val imageRef = storageReference!!.child("image/"+ UUID.randomUUID())
//                imageRef.putFile(filePath!!)
//
//                    .addOnSuccessListener {
//
//                        progressDialog.dismiss()
//                        Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener{
//                        progressDialog.dismiss()
//                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnProgressListener {taskSnapShot ->
//                        val progress = 100.0 * taskSnapShot.bytesTransferred/taskSnapShot.totalByteCount
//                        progressDialog.setTitle("Uploading image " + progress.toInt() + "%...")
//
//                    }


            alertDialog.setTitle("Uploading")
            alertDialog.show()

//            val uploadTask = storageReference!!.putFile(detailsImages)
            val uploadTask = storageReference!!.putFile(filePath!!)
            Log.d("uploadTask", uploadTask.toString())

            val task = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful){
                    Toast.makeText(this@AddDetailsActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
                storageReference!!.downloadUrl

            }.addOnCompleteListener{ task ->
                if (task.isSuccessful){

                    val downloadUrl = task.result
                    image = downloadUrl!!.toString().substring(0,downloadUrl.toString().indexOf("&token"))
//                    alertDialog.setTitle("Uploading")
//                    alertDialog.show()

                    val tireProduct = TireProduct(id,  width, profile, diameter, manufacturer, seasonality,
                        condition, image, price, user!!.uid)
//            val intent = Intent(this, TiresActivity::class.java)
                    tireProductViewModel.addTireProduct(tireProduct)
//                    Log.d("DIRECTLINK", url)
//                    alertDialog.setTitle("Добавлено")
                    alertDialog.dismiss()
                    Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show()

//                    Picasso.get().load(url).into(DetailsImageView)

                }
            }




            detailsImages.clear()
            addImagesAdapter.notifyDataSetChanged()

            rdCondition.clearCheck()
            rdSeasonality.clearCheck()
            priceInput.text.clear()
//            DetailsImageView0.setImageBitmap(null)
//            DetailsImageView1.setImageBitmap(null)
//            DetailsImageView2.setImageBitmap(null)
//            DetailsImageView3.setImageBitmap(null)


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