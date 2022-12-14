package com.example.gps

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.viewbinding.ViewBinding

object Utils {
    //Pantalla necesitan referencia
    //de la pantalla representada en codigo..
    var binding:ViewBinding?=null
    //Funcion para estimar los pixeles en base a
    //la densidad de pixeles por pantalla
    fun dp(pixeles:Int):Int{
        if(binding==null)return 0
        val scale= binding!!.root.resources.displayMetrics.density
        return (scale*pixeles*0.5f).toInt()
    }
    //Funcion para convertir vectores a maps de bits

    fun getBitmapFromVector(context: Context,resId:Int):Bitmap?{
        return AppCompatResources.getDrawable(context,resId)?.toBitmap()
    }
}