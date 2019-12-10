package com.pmlnunes.healthcode.views.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.kloadingspin.KLoadingSpin
import com.pmlnunes.healthcode.FragmentListener
import com.pmlnunes.healthcode.R
import com.pmlnunes.healthcode.models.Product
import com.pmlnunes.healthcode.switchToProductFragment
import com.pmlnunes.healthcode.switchToScanFragment
import com.pmlnunes.healthcode.views.fragments.HistoryFragment
import com.pmlnunes.healthcode.views.fragments.ResultsFragment
import com.pmlnunes.healthcode.views.fragments.ScanFragment

const val API_RESPONSE_STATUS = "productScanStatus"

const val PROD_NOT_FOUND = "productNotFound"
const val PROD_FOUND = "productFound"
const val PRODUCT = "product"

const val NO_PRODUCTS_SCANNED = "no_history"
const val PRODUCTS_SCANNED = "prodhistory"

const val PREFERENCE_FILE_KEY = "sharedprefs"





class MainActivity : AppCompatActivity(), FragmentListener, ResultsFragment.ProductViewerScanner {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET
            ),

            1
        )

        setSupportActionBar(findViewById(R.id.toolbar))



        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        var fragment = ScanFragment(this)

        transaction.add(
            R.id.fragment_container,
            fragment
        )
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.barcode -> {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(
                R.id.fragment_container,
                ScanFragment(this)
            )
            transaction.commit()


            true
        }
        R.id.history -> {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            HistoryFragment(this)
            transaction.replace(
                R.id.fragment_container,
                HistoryFragment(this)
            )
            transaction.commit()

            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    override fun addProductToHistory(product: Product?) {
        val sharedPref = getSharedPreferences(
            PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        with (sharedPref!!.edit()) {

            var prodArray = sharedPref.getString(
                com.pmlnunes.healthcode.views.activities.PRODUCTS_SCANNED,
                com.pmlnunes.healthcode.views.activities.NO_PRODUCTS_SCANNED
            )


            var productList = kotlin.collections.mutableListOf<Product>()

            if(prodArray !== com.pmlnunes.healthcode.views.activities.NO_PRODUCTS_SCANNED){
                productList = com.beust.klaxon.Klaxon().parseArray<Product>(prodArray!!)!!.toMutableList()
            }


            if(productList?.size == 10) {
                productList?.drop(1);
            }

            var mutableList =
                productList?.plus(
                    product
                )

            var jsonArray = com.google.gson.Gson().toJson(mutableList)

            putString(com.pmlnunes.healthcode.views.activities.PRODUCTS_SCANNED, jsonArray)
            commit()

        }
    }

    override fun retryScan() {
        switchToScanFragment()
    }

    override fun showInfoAboutProduct(product: Product?) {
        switchToProductFragment(product)
    }





}
