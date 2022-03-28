package com.ndev.noter

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getExternalFilesDirs
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.io.File
import java.util.*
import java.util.jar.Manifest



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val FILE_NAME = "photo.jpg"
//Request code pentru poza
private const val REQUEST_CODE = 42
private lateinit var photoFile: File

/// La fel pentru orice fragment
class SettingsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    private fun getPhotoFile(fileName: String): File {

        val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            loadImageView.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val locview:View = inflater.inflate(R.layout.fragment_settings, container, false)


        val opCamera = locview?.findViewById<Button>(R.id.openCameraButtonSettingsFragment)
        opCamera?.setOnClickListener {

            Toast.makeText(activity, "Deschide camera", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
            val fileProvider = FileProvider.getUriForFile(
                Objects.requireNonNull(requireActivity().getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(activity, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }

        }


        return locview
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}