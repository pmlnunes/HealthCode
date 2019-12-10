package com.pmlnunes.healthcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pmlnunes.healthcode.models.Product
import com.pmlnunes.healthcode.views.activities.*
import com.pmlnunes.healthcode.views.fragments.ResultsFragment
import com.pmlnunes.healthcode.views.fragments.RetryFragment
import com.pmlnunes.healthcode.views.fragments.ScanFragment

fun MainActivity.switchToProductFragment(product : Product?){

    if(product!=null){
        var args = Bundle()

        args.putString(API_RESPONSE_STATUS, PROD_FOUND)
        args.putParcelable(PRODUCT, product)

        val resultsFragment = ResultsFragment()

        resultsFragment.setProductListener(this)

        resultsFragment.arguments = args

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, resultsFragment)
        transaction.commit()

    }
    else{
        var args = Bundle()

        args.putString(API_RESPONSE_STATUS, PROD_NOT_FOUND)

        val retryFragment = RetryFragment(this)
        retryFragment.arguments = args

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, retryFragment)
    }


}

fun MainActivity.switchToScanFragment(){
    val scanFragment = ScanFragment(this)
    val manager = supportFragmentManager
    val transaction = manager.beginTransaction()
    transaction.replace(R.id.fragment_container, scanFragment)
    transaction.commit()

}
