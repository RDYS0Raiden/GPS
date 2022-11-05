package com.example.gps

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
}