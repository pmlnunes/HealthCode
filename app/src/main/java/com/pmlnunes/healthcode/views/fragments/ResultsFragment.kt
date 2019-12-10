package com.pmlnunes.healthcode.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pmlnunes.healthcode.R
import com.pmlnunes.healthcode.models.Product
import com.pmlnunes.healthcode.views.activities.*
import com.squareup.picasso.Picasso
import com.vaibhavlakhera.circularprogressview.CircularProgressView

class ResultsFragment : Fragment() {


    internal lateinit var callback: ProductViewerScanner


    private var mProduct: Product? = null

    private var mFoodInfo: TextView? = null

    private var mProductImg: ImageView? = null
    private var mProductName: TextView? = null


    private var mCircularProgressSugar: CircularProgressView? = null
    private var mCircularProgressFat: CircularProgressView? = null
    private var mCircularProgressSatFat: CircularProgressView? = null
    private var mCircularProgressSalt: CircularProgressView? = null


    fun setProductListener(callback: ProductViewerScanner) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        mProduct = arguments!!.getParcelable<Product>(PRODUCT)
        callback.addProductToHistory(mProduct)


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


        fillInfoFromProduct()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun fillInfoFromProduct(){

        Picasso.get().load(mProduct!!.picture).into(mProductImg);

        var productName = mProduct?.name
        var fat = mProduct?.fat
        var sat = mProduct?.saturated
        var salt = mProduct?.salt
        var sugar = mProduct?.sugar


        mProductName!!.text = productName

        mCircularProgressFat?.setProgress(fat?.substringBefore('.')!!.toInt(), animate = true)
        mCircularProgressFat?.setFillColorRes(mProduct!!.fatPrimaryColor)
        mCircularProgressFat?.setProgressColorRes(mProduct!!.fatSecondaryColor)

        mCircularProgressSatFat?.setProgress(sat?.substringBefore('.')!!.toInt(), animate = true)
        mCircularProgressSatFat?.setFillColorRes(mProduct!!.saturatedPrimaryColor)
        mCircularProgressSatFat?.setProgressColorRes(mProduct!!.saturatedSecondaryColor)

        mCircularProgressSalt?.setProgress(salt?.substringBefore('.')!!.toInt(), animate = true)
        mCircularProgressSalt?.setFillColorRes(mProduct!!.saltPrimaryColor)
        mCircularProgressSalt?.setProgressColorRes(mProduct!!.saltSecondaryColor)

        mCircularProgressSugar?.setProgress(sugar?.substringBefore('.')!!.toInt(), animate = true)
        mCircularProgressSugar?.setFillColorRes(mProduct!!.sugarPrimaryColor)
        mCircularProgressSugar?.setProgressColorRes(mProduct!!.sugarSecondaryColor)





    }

    interface ProductViewerScanner{
        fun addProductToHistory(product: Product?)
    }







}
