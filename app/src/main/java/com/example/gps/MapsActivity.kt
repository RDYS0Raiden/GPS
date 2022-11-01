package com.example.gps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.gps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var contador=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo,20f))


        //Evento de click sobre el mapa
        mMap.setOnMapClickListener {
            if(contador<5){
                contador++
                mMap.addMarker(MarkerOptions().position(it).title("Mi nueva posicion").snippet("${it.latitude}, ${it.longitude}").draggable(true))
            }else{
                Toast.makeText(this,"numero de marcadores maximos",Toast.LENGTH_SHORT).show()
            }
            /*
            * Configuracion de controles de Ui
            * y Gestures del mapa
            * */
            mMap.uiSettings.apply {
                isZoomControlsEnabled=true // controles de zoom
                isCompassEnabled=true// habilita el compas de la orientacion
                isMapToolbarEnabled=true// botones complementarios del mapa

            }
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            /*
        mMap.apply{
        setMinZoomPreference(14f)
        setMaxZoomPreference(18f)
        }
        */

            /*
             // Add a marker in Sydney and move the camera
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            */
            /*
             mMap.addMarker(MarkerOptions().position(Monticulo).title("Marker in Monticulo").draggable(true))
            mMap.addMarker(MarkerOptions().position(ValleDeLaLuna).title("Marker in Valle de la Luna").draggable(true))
            mMap.addMarker(MarkerOptions().position(Zoologico).title("Marker in Zoologico").draggable(true))
            mMap.addMarker(MarkerOptions().position(univalle).title("Marker in univalle").draggable(true))


            val cameraUnivalle = CameraPosition.builder()
                .bearing(240f)
                .tilt(75f)
                .zoom(16f)
                .target(univalle)
                .build()
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraUnivalle))

            lifecycleScope.launch{
                delay(5000)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Monticulo,20f))
                delay(7000)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Monticulo,20f))
            }
            mMap.setOnMapClickListener {

                mMap.addMarker(MarkerOptions().position(it).title("Mi nueva posicion").snippet("${it.latitude}, ${it.longitude}"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            }
            */
        }

    }

}