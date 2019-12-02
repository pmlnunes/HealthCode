package com.pmlnunes.healthcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.nikartm.button.FitButton
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

val REQUEST_IMAGE_CAPTURE = 1

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ScanFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanFragment : Fragment() {


    private var mButton: FitButton? = null
    private var mCurrentPhotoPath: String? = null
    private var mBarcode: String? = null
    private var mImageBitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.scan_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mButton = view?.findViewById(R.id.button) as FitButton
        mButton!!.setOnClickListener(){
            takePicture()
        }

        super.onViewCreated(view, savedInstanceState)
    }



private fun takePicture(){
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (cameraIntent.resolveActivity(activity!!.packageManager) != null) {
        // Create the File where the photo should go
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (ex: IOException) {
            // Error occurred while creating the File
            Log.i(
                "ActTag", "IOException")
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            cameraIntent.putExtra(
                MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                    this.context!!,
                    "com.pmlnunes.healthcode.provider", //(use your app signature + ".provider" )
                    photoFile))
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        }
    }
}


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val image = File.createTempFile(
            imageFileName, // prefix
            ".jpg", // suffix
            storageDir      // directory
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun callFoodApi() {
        val url = "https://world.openfoodfacts.org/api/v0/product/" + mBarcode + ".json"

        if (mBarcode!= null){

            val queue = Volley.newRequestQueue(activity)
            val args = Bundle()



            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
//                    mFoodInfo!!.text = response.toString(2)
//                    gatherInfoFromResponse(response)

                    if(response.getString("status_verbose").equals("product not found")){
                        args.putBoolean("notFound", true)
                        val retryFragment = RetryFragment()
                        retryFragment.arguments = args
                        val manager = activity!!.supportFragmentManager
                        val transaction = manager.beginTransaction()
                        transaction.replace(R.id.fragment_container, retryFragment)
                        transaction.commit()
                    }else{
                        args.putString("scanInfo", response.toString(2))
                        val resultsFragment = ResultsFragment()
                        resultsFragment.arguments = args
                        val manager = activity!!.supportFragmentManager
                        val transaction = manager.beginTransaction()
                        transaction.replace(R.id.fragment_container, resultsFragment)
                        transaction.commit()
                    }


                },
                Response.ErrorListener { error ->

                }
            )

            queue.add(jsonObjectRequest)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                var options = BitmapFactory.Options ();
                options.inSampleSize = 4;
                try {
                    mImageBitmap = BitmapFactory.decodeFile(
                        mCurrentPhotoPath,
                        options
                    )
                }catch (e: Exception){
                    throw e
                }





                val barcodeDetector = BarcodeDetector.Builder(context!!)
                    .build();

                val frame = Frame.Builder().setBitmap(mImageBitmap).build()
                val barcodes = barcodeDetector.detect(frame)

                if(barcodes.size()==0){
                    val retryFragment = RetryFragment()
                    val manager = activity!!.supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragment_container, retryFragment)
                    transaction.commit()
                }else{
                    val thisCode = barcodes.valueAt(0)
                    mBarcode = thisCode.rawValue

                    callFoodApi();
                }


            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }








}
