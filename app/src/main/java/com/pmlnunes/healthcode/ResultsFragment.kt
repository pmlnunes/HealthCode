package com.pmlnunes.healthcode

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vaibhavlakhera.circularprogressview.CircularProgressView
import org.json.JSONObject

class ResultsFragment : Fragment() {


    private var mFoodInfo: TextView? = null

    private var mProductImg: ImageView? = null
    private var mProductName: TextView? = null


    private var mCircularProgressSugar: CircularProgressView? = null
    private var mCircularProgressFat: CircularProgressView? = null
    private var mCircularProgressSatFat: CircularProgressView? = null
    private var mCircularProgressSalt: CircularProgressView? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.results_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mProductName = view?.findViewById(R.id.productName) as TextView
        mProductImg = view?.findViewById(R.id.productImg) as ImageView

        mCircularProgressSugar = view?.findViewById(R.id.progressViewSugar) as CircularProgressView
        mCircularProgressFat = view?.findViewById(R.id.progressViewFat) as CircularProgressView
        mCircularProgressSatFat = view?.findViewById(R.id.progressViewSaturatedFat) as CircularProgressView
        mCircularProgressSalt = view?.findViewById(R.id.progressViewSalt) as CircularProgressView



        val args = arguments
        val index = args!!.getString("scanInfo", "")
        gatherInfoFromResponse(JSONObject(index))

        super.onViewCreated(view, savedInstanceState)
    }

    private fun gatherInfoFromResponse(response: JSONObject?) {

        var product = response!!.getJSONObject("product")
        var nutriments = product!!.getJSONObject("nutriments")

        Picasso.get().load(product!!.get("image_url").toString()).into(mProductImg);

        mProductName!!.text = product.get("product_name").toString()



        var fat = nutriments.get("fat_100g").toString()
        var sat = nutriments.get("saturated-fat_100g").toString()
        var salt = nutriments.get("salt_100g").toString()
        var sugar = nutriments.get("sugars_100g").toString()






        mCircularProgressFat?.setProgress(fat.substringBefore('.').toInt(), animate = true)
        mCircularProgressSatFat?.setProgress(sat.toString().substringBefore('.').toInt(), animate = true)
        mCircularProgressSalt?.setProgress(salt.toString().substringBefore('.').toInt(), animate = true)
        mCircularProgressSugar?.setProgress(sugar.toString().substringBefore('.').toInt(), animate = true)



        if (fat.toDouble() < 3 ){
            mCircularProgressFat?.setFillColorRes(R.color.green)
            mCircularProgressFat?.setProgressColorRes(R.color.green2)

        }else if(fat.toDouble() >= 3 && fat.toDouble() <= 17.5){
            mCircularProgressFat?.setFillColorRes(R.color.orange)
            mCircularProgressFat?.setProgressColorRes(R.color.orange2)

        }else if(fat.toDouble() > 17.5){
            mCircularProgressFat?.setFillColorRes(R.color.red)
            mCircularProgressFat?.setProgressColorRes(R.color.red2)

        }

        if (sat.toDouble() < 1.5 ){
            mCircularProgressSatFat?.setFillColorRes(R.color.green)
            mCircularProgressSatFat?.setProgressColorRes(R.color.green2)

        }else if(sat.toDouble() >= 1.5 && sat.toDouble() <= 5){
            mCircularProgressSatFat?.setFillColorRes(R.color.orange)
            mCircularProgressSatFat?.setProgressColorRes(R.color.orange2)

        }else if(sat.toDouble() > 5){
            mCircularProgressSatFat?.setFillColorRes(R.color.red)
            mCircularProgressSatFat?.setProgressColorRes(R.color.red2)

        }

        if (salt.toDouble() < 0.3 ){
            mCircularProgressSalt?.setFillColorRes(R.color.green)
            mCircularProgressSalt?.setProgressColorRes(R.color.green2)
        }else if(salt.toDouble() >= 0.3 && salt.toDouble() <= 1.5){
            mCircularProgressSalt?.setFillColorRes(R.color.orange)
            mCircularProgressSalt?.setProgressColorRes(R.color.orange2)
        }else if(salt.toDouble() > 1.5){
            mCircularProgressSalt?.setFillColorRes(R.color.red)
            mCircularProgressSalt?.setProgressColorRes(R.color.red2)
        }

        if (sugar.toDouble() < 5 ){
            mCircularProgressSugar?.setFillColorRes(R.color.green)
            mCircularProgressSugar?.setProgressColorRes(R.color.green2)

        }else if(sugar.toDouble() >= 5 && sugar.toDouble() <= 22.5){
            mCircularProgressSugar?.setFillColorRes(R.color.orange)
            mCircularProgressSugar?.setProgressColorRes(R.color.orange2)

        }else if(sugar.toDouble() > 22.5){
            mCircularProgressSugar?.setFillColorRes(R.color.red)
            mCircularProgressSugar?.setProgressColorRes(R.color.red2)

        }


    }




}