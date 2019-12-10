package com.pmlnunes.healthcode.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.kloadingspin.KLoadingSpin
import com.pmlnunes.healthcode.FragmentListener
import com.pmlnunes.healthcode.R
import com.pmlnunes.healthcode.presenters.ScanPresenter


class ScanFragment(val callback: FragmentListener) : Fragment() {


    private lateinit var codeScanner: CodeScanner
    private lateinit var mLoadingSpin: KLoadingSpin

    private var mScanController: ScanPresenter? = null




    override fun onCreate(savedInstanceState: Bundle?) {

        mScanController = ScanPresenter(this)

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

                mScanController?.getProductInfoFromBarcode(it.text)


            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        super.onViewCreated(view, savedInstanceState)
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
