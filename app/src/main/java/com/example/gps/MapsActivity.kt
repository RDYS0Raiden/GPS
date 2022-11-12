package com.example.gps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import com.example.gps.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var contador=0;

    private val PERMISSION_ID = 42
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
        //Activar evento listener de conjunto de botones
        setupToggleButtons()
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

        //Como delimitar el area de la zona

        //mMap.setLatLngBoundsForCameraTarget(univalleBounds)

        //Activar la posicion actual en el mapa

        //Evaluar permisisos de GPS.....


            if (hasGPSEnabled()){
                if (allPermissionsGrnated()){
                    mMap.isMyLocationEnabled=true
                }
                else
                {
                    requestPermissionUser()
                }
            }else{ goToEnableGPS() }



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
            /**
             *configuracion personalizada,estilo de mapa
             *
             */

            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_style))
        /**
         * configuracion y personalizacion de marcodores
         * estilos formas y eventos
         * */

        val univalleMarcador=mMap.addMarker(
            MarkerOptions().title("mi universidad").position(univalle)
        )
        univalleMarcador?.run{
            //setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))//cambio de color de los colores definidos por defecto
            //setIcon(BitmapDescriptorFactory.defaultMarker(150f))//cambio de color personalizado
            //setIcon(BitmapDescriptorFactory.fromResource(R.drawable.zapatos_de_mujer))//con un icono personalizado con imagen
            /*
            Utils.getBitmapFromVector(this@MapsActivity,R.drawable.ic_thumb_up_32)?.let{
                setIcon(BitmapDescriptorFactory.fromBitmap(it))
            }//Marcador personalizado a partir de imagenes vectoriales de la libreria de android
            */

            setIcon(BitmapDescriptorFactory.fromResource(R.drawable.shop))//si quieres usar las imagenes de por defecto descomentar la parte de arriba
            rotation= 145f
            setAnchor(0.5f,0.5f)//punto de rotacion central
            isFlat=true// el marcador rota o no con el mapa
            isDraggable=true// se puede arrastrar el marcador
            snippet="""
                Carrera de ingeniria de sistemas
                ${univalle.longitude},${univalle.latitude}
            """.trimIndent()



        }

        //evento en markers
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMarkerDragListener(this)
        /**
         * trazado de rutas
         * partiendo de dibujar lineas en el mapa
         * entre puntos de coordenadas
         * (Polyline)
         */
        setupPolyline()
        }
        //Dibuja una linea  en el mapa
    private fun setupPolyline() {
        //de tener una ruta en formato de array o lista
        val miRuta= mutableListOf(univalle,stadium,cementeriojudios,salchisalvaje,ValleDeLaLuna,canchaVenus,Monticulo,Zoologico,parke)
            //se configura y crea la linea
            val polyline=mMap.addPolyline(PolylineOptions()
                .color(Color.YELLOW)//color de la linea
                .width(12f)// define ancho de la linea
                .clickable(true)// permite que la linea le puedas hacer click
                .geodesic(true)//define la linea repetando la curvatura de la tierra
                )
            //tienes que indicar los puntos los que se van a dibujar
            polyline.points=miRuta

            lifecycleScope.launch(){
                val misRutas = mutableListOf<LatLng>()
                for (punto in miRuta) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto,20f))
                    misRutas.add(punto)
                    polyline.points = misRutas
                    delay(2_000)

                   //
                }
                //configurar al interseccion o union de lineas
                polyline.jointType=JointType.BEVEL
                //Personalizar el patron de la linea
                //1)Linea continua por defecto
                //2)que la linea sea dibujada por puntos separados por un espacio
                //3) segmentos de linea separados por espacion o guiones
                polyline.pattern= listOf(Dot(),Gap(32f),Dash(32f),Gap(32f))
            }


    }

    private fun setupToggleButtons(){
        binding.toggleGroup.addOnButtonCheckedListener {
                group, checkedId, isChecked ->  if(isChecked){
            mMap.mapType = when(checkedId){
                R.id.btnNormal -> GoogleMap.MAP_TYPE_NORMAL
                R.id.btnHibrido -> GoogleMap.MAP_TYPE_HYBRID
                R.id.btnSatelital -> GoogleMap.MAP_TYPE_SATELLITE
                R.id.btnTerreno -> GoogleMap.MAP_TYPE_TERRAIN
                else -> GoogleMap.MAP_TYPE_NONE
            }
        }
        }
    }
    private fun goToEnableGPS() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    private fun hasGPSEnabled():Boolean {
        //Manager: es el que lleva la batuta o es el que orgranizagestiona
        // lo referido al manejo de ciertos sevicios o recurso
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager //esta es la libreria para la localizacion
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun allPermissionsGrnated():Boolean =
        MainActivity.PERMISSION_GRANTED.all{
            ActivityCompat.
            checkSelfPermission(baseContext, it) == //por que el contexto por que no puede ser this, pues por que stamos dentro de un mabito anonimo o flecha
                    PackageManager.PERMISSION_GRANTED
        }
    /*
    private fun checkPermission():Boolean{
        return ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    */

    private fun requestPermissionUser(){
        //Lanzar la ventana al usuario para solicitarle que habilite el permisos o los deniegue

        //to_do lo que tiene que ver con revisar permisso se enuentra aca ActivitiCompact
        ActivityCompat.requestPermissions(
            this,
            MainActivity.PERMISSION_GRANTED, //no se pone una lista por que como requerimiento dice que necesita u array y no una lista
            PERMISSION_ID //42 por que es el codigo de acceso
        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
      //marker es el marcador al que le has hecho click

        mMap.setInfoWindowAdapter(InfoWndowAdapter(this))
        /*
        Toast.makeText(this,"${marker.position.latitude},${marker.position.longitude}",
        Toast.LENGTH_LONG).show()
*/
        return false

    }
    // dice cuando el marcador esta siendo arrastrando por el mapa
    override fun onMarkerDrag(marker: Marker) {
        //marker : el marcador que estas arrastrando
        binding.toggleGroup.visibility= View.INVISIBLE
        marker.alpha=0.4f
    }
    // cuando se suelta el marcador luego de haberlo arrastrado
    override fun onMarkerDragEnd(marker: Marker) {
        binding.toggleGroup.visibility=View.VISIBLE
        marker.alpha=1.0f
        //sirve para desplegar la ventana de informacion
        //llamada infoWindow
        marker.showInfoWindow()
    }
    //esto es cuando esta empezando a ser arrastrado el marcador
    override fun onMarkerDragStart(marker: Marker) {
        //ocultar la ventana de informacion
        marker.hideInfoWindow()
    }

}
