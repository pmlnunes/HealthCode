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
import android.widget.Toast
import androidx.core.content.FileProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.kloadingspin.KLoadingSpin
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


    private lateinit var codeScanner: CodeScanner
    private lateinit var mLoadingSpin: KLoadingSpin
    private var mCurrentPhotoPath: String? = null
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


        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        mLoadingSpin = view.findViewById(R.id.loadingSpin)
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                mLoadingSpin.startAnimation()
                mLoadingSpin.setIsVisible(true)
                callFoodApi(it.text)

                //Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        super.onViewCreated(view, savedInstanceState)
    }




    private fun callFoodApi(barcode : String) {
        val url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json"

        if (barcode!= null){

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


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }






}
