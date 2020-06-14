package tatsunomiya.com.autoslideshowapp

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var switch = 0

    private val PERMISSIONS_REQUEST_CODE = 100


    var mCountNum = 0

    private var mTimerSec = 0.0


    var imageUriArray: ArrayList<Uri> = ArrayList()


    var mHandler = Handler()

    var mTimer: Timer? = null

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

        back_button.setOnClickListener {
            backButton()
        }

        resume_button.setOnClickListener {

            if(switch == 0) {

                switch = 1

                resume_button.text= "停止"

               back_button.setEnabled(false)
               next_button.setEnabled(false)

                if (imageUriArray.count() != 0) {

                    if (mTimer == null) {
                        mTimer = Timer()
                        mTimer!!.schedule(object : TimerTask() {
                            override fun run() {
                                mTimerSec += 0.1
                                mHandler.post {
                                    mCountNum += 1
                                    var num = mCountNum % imageUriArray.count();

                                    imageView.setImageURI(imageUriArray.get(num));

                                }
                            }
                        }, 100, 2000) // 最初に始動させるまで 100ミリ秒、ループの間隔を 100ミリ秒 に設定
                    }
                }

        } else if(switch == 1) {


                resume_button.text= "再生"


                back_button.setEnabled(true)
                next_button.setEnabled(true)
                //カウンターを止める
                if (mTimer != null){
                    mTimer!!.cancel()
                    mTimer = null
                }

                switch = 0

                }
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

        val resolver = contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursor!!.moveToFirst()) {

            do {

                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)

                val id = cursor.getLong(fieldIndex)

                val imageUri =

                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)


                imageUriArray.add(imageUri);
//                imageView.setImageURI(imageUri)

                Log.d("Android", imageUri.toString())


            } while (cursor.moveToNext())


        }
        imageView.setImageURI(imageUriArray.get(0));
        cursor.close()


//        if (cursor.moveToFirst()) {
////            do {
//            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
//            val id = cursor.getLong(fieldIndex)
//            val imageUri =
//                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
//
////            imageView.setImageURI(imageUri)
//        }while (cursor.moveToNext())
//
////    } while (cursor.moveToNext())
//
//    }
//
//    mImageView.setImageURI(imageUriArray.get(0))
//
//
//            cursor.close()
//        }
//
//
//
//
//    }
    }
    private fun nextButton() {

        if(imageUriArray.count() != 0){
            mCountNum += 1;
            var num = mCountNum % imageUriArray.count();
            imageView.setImageURI(imageUriArray.get(num));
//            mTimerText.setText(String.format("%d枚目を表示中", num + 1));
        } else {
//            mTimerText.setText(String.format("写真へのアクセスを許可した後に，画像を1枚以上追加してください"));
        }
    }

    private fun backButton(){
        if(imageUriArray.count() != 0){
            mCountNum -= 1;
            var num = mCountNum % imageUriArray.count();
            imageView.setImageURI(imageUriArray.get(num));
//            mTimerText.setText(String.format("%d枚目を表示中", num + 1));
        } else {
//            mTimerText.setText(String.format("写真へのアクセスを許可した後に，画像を1枚以上追加してください"));
        }
    }

    }






