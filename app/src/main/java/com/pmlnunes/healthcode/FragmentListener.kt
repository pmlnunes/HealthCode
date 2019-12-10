package com.pmlnunes.healthcode

import com.pmlnunes.healthcode.models.Product


interface FragmentListener{

    fun showInfoAboutProduct(product: Product?)
    fun retryScan()
}