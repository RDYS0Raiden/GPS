package com.example.gps

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.gps.Coordenadas.Monticulo
import com.example.gps.Coordenadas.ValleDeLaLuna
import com.example.gps.Coordenadas.Zoologico
import com.example.gps.Coordenadas.canchaVenus
import com.example.gps.Coordenadas.casa
import com.example.gps.Coordenadas.casaJhere
import com.example.gps.Coordenadas.cementeriojudios
import com.example.gps.Coordenadas.parke
import com.example.gps.Coordenadas.plazaAvaroa
import com.example.gps.Coordenadas.punto0Lapaz
import com.example.gps.Coordenadas.salchisalvaje
import com.example.gps.Coordenadas.stadium
import com.example.gps.Coordenadas.univalle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.gps.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var contador=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.binding=binding
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        //Que El mapa se carga Asincronamente
        // no satura el proceso principal o de la UI

        //este parametro necesita una funcion de orden superior
        mapFragment.getMapAsync(this)//en este apartado se podria usar la funcion anonima
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    //Respuesta cunado el mapa esta listo para trabajar
    // el parametro que tienen devuelve el mapa de
    // Google listo y configurado
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        //concepto numero 1 es tener una ubicacion
        mMap = googleMap

        //ustede puede Delimitar el rango de Zoom de la camara
        // para evitar que el usuario haga un
        //zoom in o out de la camara
        mMap.apply {
            setMinZoomPreference(15f)
            setMaxZoomPreference(19f)
        }

        // se define coordenadas en un objeto
        //latng que conjunciona latitud y longitud
        val tokyo = LatLng(35.69880393930904, 139.77589632715151)
        //concepto numero 2 debemos tener un marcador
        // trabajos de Marcadores .... Tachuelas rojas
        mMap.addMarker(MarkerOptions()
            .position(tokyo)
            .title("mi lugar favorito")
            .snippet("${tokyo.latitude},{${tokyo.longitude}}"))
        //concepto numero 3 es posicionar la camara en la ubicacion de
        //preferencia
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo,20f))
        /**
         * Configuracion de su camera personalizada
        **/

        val camaraPersonalizada= CameraPosition.Builder()
            .target(univalle)//esto es donde apunta la camara
            .zoom(16f) // es el zoom personalizado 15 y 20 es generalmente para calles 20 es para edificios
            .tilt(80f) //es el Angulo de la inclinacion de la camara y no hay que ser agresivos, no se puede poder angulos tan agresivos
            .bearing(220f)// cambio de orientacion de 0 a 360
            .build()

      // mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camaraPersonalizada))
        /**
         *Movimiento de la camara (animacion de la camara)
         * Plus--- uso standar de corrutinas
         */

/*
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(univalle,17f))
        //Corrutinas para apreciar mejor el movimiento
        lifecycleScope.launch{
            delay(5000)
            //Para mover la camara entre puntos en el mapa
            //les recomiendo usar una animacion que haga una transicion
            //de movimiento ... se usa el metodo
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casaJhere,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stadium,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ValleDeLaLuna,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(salchisalvaje,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Zoologico,20f))
            delay(5000)
            //
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(canchaVenus,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(canchaVenus,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Monticulo,20f))
            delay(5000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parke,20f))
            delay(5000)
            mMap.addMarker(MarkerOptions().position(casa).title("mi destino final").draggable(true))
        }
*/
        /**
         * Bounds para delimitar areas de accion
         * en el mapa, armar sesgos.
         */

        val univalleBounds=LatLngBounds(plazaAvaroa, cementeriojudios)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto0Lapaz,15f))
        lifecycleScope.launch{
            delay(3_500)
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(univalleBounds,32))
            //si tu te quieres mover en el punto central del cuadrante definido
            delay(2000)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(univalleBounds.center,18f))
        }

        //Como delimitar el area
        mMap.setLatLngBoundsForCameraTarget(univalleBounds)
        //Activar la posicion actual en el mapa
        //Evaluar permisisos de GPS.....
        mMap.isMyLocationEnabled=true


        //Evento de click sobre el mapa
       // mMap.setOnMapClickListener {
         /*
            if(contador<5){
                contador++
                mMap.addMarker(MarkerOptions().position(it).title("Mi nueva posicion").snippet("${it.latitude}, ${it.longitude}").draggable(true))
            }else{
                Toast.makeText(this,"numero de marcadores maximos",Toast.LENGTH_SHORT).show()
            }
            */

            /**
            * Configuracion de controles de Ui
            * y Gestures del mapa
            * */
        //movimiento por pixeles
        /*
            lifecycleScope.launch{
                delay(5_000)
                for (i in 0..100){}
                mMap.animateCamera(CameraUpdateFactory.scrollBy(0f,140f))
                delay(500)
            }
*/




            mMap.uiSettings.apply {
                isMyLocationButtonEnabled=true// esto activa el botton para posicionar al centro del mapa
                isZoomControlsEnabled=true // controles de zoom
                isCompassEnabled=true// habilita el compas de la orientacion
                isMapToolbarEnabled=true// botones complementarios del mapa
                isRotateGesturesEnabled=false// ya no pueden rotar el mapa (el control)
                isTiltGesturesEnabled=false // no pueden inclinar la camara
                isZoomGesturesEnabled=true//habilitar p desabilitar gestos de zoom
            }


        }

    }
