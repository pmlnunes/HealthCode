package com.pmlnunes.healthcode.models

import android.os.Parcel
import android.os.Parcelable
import com.pmlnunes.healthcode.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val name: String, val picture: String, val sugar: String,
              val fat: String, val saturated: String, val salt: String): Parcelable {

    var fatPrimaryColor: Int = 0
    var fatSecondaryColor: Int = 0

    var sugarPrimaryColor: Int = 0
    var sugarSecondaryColor: Int = 0

    var saturatedPrimaryColor: Int = 0
    var saturatedSecondaryColor: Int = 0

    var saltPrimaryColor: Int = 0
    var saltSecondaryColor: Int = 0

    init{
        if (fat.toDouble() < 3 ){
            fatPrimaryColor = R.color.green
            fatSecondaryColor = R.color.green2

        }else if(fat.toDouble() >= 3 && fat.toDouble() <= 17.5){
            fatPrimaryColor = R.color.orange
            fatSecondaryColor = R.color.orange2

        }else if(fat.toDouble() > 17.5){
            fatPrimaryColor = R.color.red
            fatSecondaryColor = R.color.red2

        }

        if (saturated.toDouble() < 1.5 ){
            saturatedPrimaryColor = R.color.green
            saturatedSecondaryColor = R.color.green2

        }else if(saturated.toDouble() >= 1.5 && saturated.toDouble() <= 5){
            saturatedPrimaryColor = R.color.orange
            saturatedSecondaryColor = R.color.orange2

        }else if(saturated.toDouble() > 5){
            saturatedPrimaryColor = R.color.red
            saturatedSecondaryColor = R.color.red2

        }

        if (salt.toDouble() < 0.3 ){
            saltPrimaryColor = R.color.green
            saltSecondaryColor = R.color.green2
        }else if(salt.toDouble() >= 0.3 && salt.toDouble() <= 1.5){
            saltPrimaryColor = R.color.orange
            saltSecondaryColor = R.color.orange2
        }else if(salt.toDouble() > 1.5){
            saltPrimaryColor = R.color.red
            saltSecondaryColor = R.color.red2
        }

        if (sugar.toDouble() < 5 ){
            sugarPrimaryColor = R.color.green
            sugarSecondaryColor = R.color.green2

        }else if(sugar.toDouble() >= 5 && sugar.toDouble() <= 22.5){
            sugarPrimaryColor = R.color.orange
            sugarSecondaryColor = R.color.orange2

        }else if(sugar.toDouble() > 22.5){
            sugarPrimaryColor = R.color.red
            sugarSecondaryColor = R.color.red2

        }
    }

}

