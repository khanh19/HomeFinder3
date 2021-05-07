package com.example.homefinder2.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homefinder2.Model.Property
import com.example.homefinder2.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_property.*
import java.io.IOException
import java.util.*

class AddPropertyActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        addpho_btn.setOnClickListener {
            //check runtime permission
            ImagePicker()
        }

        addprop_btn.setOnClickListener {
            saveImage()
        }
    }

    private fun ImagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                add_pho?.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }



    private fun saveInfo(imageID: UUID) {
        val price = cost.text.toString().trim()
        val descript = script.text.toString().trim()
        val address = addy.text.toString().trim()

        if (price.isEmpty()) {
            cost.error = "Please fill in the blank"
            return
        }

        if (descript.isEmpty()) {
            script.error = "Please fill in the blank"
            return
        }

        if (address.isEmpty()) {
            addy.error = "Please fill in the blank"
            return
        }

        val refer = FirebaseDatabase.getInstance().getReference("Property")
        val id = UUID.randomUUID()

        val prop = Property(imageID.toString(), price, descript, address)
        refer.child(id.toString()).setValue(prop)


        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun saveImage()
    {

        if(filePath != null){
            val imageID = UUID.randomUUID()
            val ref = storageReference?.child("Property Pics/$imageID")
            ref?.putFile(filePath!!)?.addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> {
                Toast.makeText(applicationContext, "Image Uploaded", Toast.LENGTH_SHORT).show()
                saveInfo(imageID)
            })?.addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(applicationContext, "Image Uploading Failed " + e.message, Toast.LENGTH_SHORT).show()
            })
        }else{
            Toast.makeText(applicationContext, "Please Select an Image", Toast.LENGTH_SHORT).show()
        }
    }
    }
