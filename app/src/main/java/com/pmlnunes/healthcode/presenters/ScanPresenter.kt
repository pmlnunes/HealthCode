package com.pmlnunes.healthcode.presenters

import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.pmlnunes.healthcode.models.Product
import com.pmlnunes.healthcode.views.fragments.ScanFragment

class ScanPresenter constructor(private val scanFragment: ScanFragment) {


    public fun getProductInfoFromBarcode(barcode: String) {

        var productToReturn : Product? = null

        val url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json"


        if (barcode!= null){

            val queue = Volley.newRequestQueue(scanFragment.context)
            val args = Bundle()



            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->


                    if(!response.getString("status_verbose").equals("product not found")){

//


                        var product = response!!.getJSONObject("product")
                        var nutriments = product!!.getJSONObject("nutriments")

                        //var labels = product.getJSONArray("labels")
                        var imageURL = product!!.get("image_url").toString()



                        var productName = product.get("product_name").toString()



                        var fat = nutriments.get("fat_100g").toString()
                        var sat = nutriments.get("saturated-fat_100g").toString()
                        var salt = nutriments.get("salt_100g").toString()
                        var sugar = nutriments.get("sugars_100g").toString()

                        productToReturn = Product(productName, imageURL, sugar, fat, sat, salt)
                        scanFragment.callback.showInfoAboutProduct(productToReturn)
                    }


                },
                Response.ErrorListener { error ->

                }
            )

            queue.add(jsonObjectRequest)
        }






    }


}