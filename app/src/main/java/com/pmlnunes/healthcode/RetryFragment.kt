package com.pmlnunes.healthcode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject

class RetryFragment : Fragment() {


    private var mButton: Button? = null
    private var mText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.barcode_notfound_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mButton = view?.findViewById(R.id.retryButton) as Button
        mText = view?.findViewById(R.id.retryReason)
        mButton!!.setOnClickListener(){
            retryScan()
        }

        val args = arguments
        val notfound = args?.getBoolean("notFound")

        if(notfound != null){
            mText!!.text = "Produto não encontrado."
        }

        super.onViewCreated(view, savedInstanceState)
    }



    private fun retryScan(){
        val scanFragment = ScanFragment()
        val manager = activity!!.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, scanFragment)
        transaction.commit()
    }



}
