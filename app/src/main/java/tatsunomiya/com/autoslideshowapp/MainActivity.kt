package tatsunomiya.com.autoslideshowapp

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 100

  open  var resolver = contentResolver
  open  var cursor =  resolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getContentsInfo()

            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_CODE
                )
            }

        } else {
            getContentsInfo()
        }


        next_button.setOnClickListener {
                        nextButton()
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getContentsInfo()
                }
        }

    }

    private fun getContentsInfo() {

//        val resolver = contentResolver
//        val cursor = resolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )


        if (cursor!!.moveToFirst()) {
            do {
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                imageView.setImageURI(imageUri)
                Log.d("Android", imageUri.toString())

            } while (cursor.moveToNext())


        }
        cursor.close()

    }

    private fun nextButton() {
//        val resolver = contentResolver
//        val cursor =  resolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            null,
//            null,
//            null,
//            null
//        )


        if (cursor!!.moveToFirst()) {

                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)



                imageView.setImageURI(imageUri)
                Log.d("Android", imageUri.toString())












        }
        cursor.close()

        }


    }
