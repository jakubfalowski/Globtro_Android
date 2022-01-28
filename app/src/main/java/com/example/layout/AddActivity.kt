package com.example.layout

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.theartofdev.edmodo.cropper.CropImage
import java.io.IOException

class AddActivity : AppCompatActivity() {

    private lateinit var edTitle: EditText
    private lateinit var edDesc: EditText
    private lateinit var btnAddLog: Button
    private lateinit var btnCamera: Button
    private lateinit var imageFromBtn: ImageView

    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: MapsAdapter? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private fun clearEditText() {
        edTitle.setText("")
        edDesc.setText("")
        edTitle.requestFocus()
    }

//        private fun initLL(){
////        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MapsAdapter()
//        recyclerView.adapter = adapter
//    }

    private fun startMainActivity() {

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LogActivity::class.java))
            finish()
        }, 1000)

    }

    private fun initView() {
        edTitle = findViewById(R.id.edTitle)
        edDesc = findViewById(R.id.edDesc)
        btnAddLog = findViewById(R.id.btnAddLog)
        imageFromBtn = findViewById(R.id.imageFromBtn)
        btnCamera = findViewById(R.id.btnCamera)
    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 9)
                .getIntent(this@AddActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }
    private lateinit var cropAcitivityResultLauncher: ActivityResultLauncher<Any?>


    private lateinit var imageToPost : Uri;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        initView()

        sqliteHelper = SQLiteHelper(this)
        btnAddLog.setOnClickListener { addPost() }

//        val getImage = registerForActivityResult(
//            ActivityResultContracts.GetContent(),
//            ActivityResultCallback {
//                imageFromBtn.setImageURI(it)
//            }
//            )
//
//
//        btnChoose.setOnClickListener {
//
//            getImage.launch("image/*")
//        }

        cropAcitivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                imageFromBtn.setImageURI(uri)
                imageToPost = uri;
//                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        btnCamera.setOnClickListener {
            cropAcitivityResultLauncher.launch(null)
        }

    }

    @Throws(IOException::class)
    private fun readBytes(contextResolver: ContentResolver, uri: Uri): ByteArray? =
        contextResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }

    private fun addPost() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        task.addOnSuccessListener {
            if (it != null) {
                val title = edTitle.text.toString()
                val description = edDesc.text.toString()
                val latitude = it.latitude
                val longtitude = it.longitude


                if (title.isEmpty() || description.isEmpty()) {
                    Toast.makeText(this, "Prosze wpisać wymagane pole ", Toast.LENGTH_SHORT).show()
                } else {
                    if (imageToPost.toString().equals("")){Toast.makeText(this, "Prosze dodaj obrazek", Toast.LENGTH_SHORT).show();return@addOnSuccessListener}

                    val image = readBytes(contentResolver,imageToPost);

                    val std2 = image?.let { it1 ->
                        PostModel(
                            title = title,
                            description = description,
                            latitude = latitude,
                            longtitude = longtitude,
                            image = it1//ByteArray(0)
                        )
                    }
                    val status2 = std2?.let { it1 -> sqliteHelper.insertPost(it1) }
                    if (status2 != null) {
                        if (status2 > -1) {
                            Toast.makeText(this, "Dodawanie postu", Toast.LENGTH_SHORT).show()
                            clearEditText()
                            startMainActivity()

                        } else {
                            Toast.makeText(this, "Nie udało się dodać nowego postu", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}
