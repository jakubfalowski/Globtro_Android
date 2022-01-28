package com.example.layout

import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.layout.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import kotlin.properties.Delegates

//import com.google.android.gms.location.LocationServices;

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var sqliteHelper: SQLiteHelper
    private var adapter: PostAdapter? = null

    private var PERMISSION_ID = 1000


    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    private fun getMaps() {
        val stdListPost = sqliteHelper.getPost()
        Log.e("Liczba postów: ", "${stdListPost.size}")

        adapter?.addPost(stdListPost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = SQLiteHelper(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()



    }
//
//    private fun getLastLocation() {
//
//        if (CheckPermission()) {
//            if (isLocationEnabled()) { //
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return
//                    //
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
//                    var location = task.result
//
//                    if (location == null) {
//                        mMap.moveCamera(
//                            CameraUpdateFactory.newLatLngZoom(
//                                LatLng(
//                                    52.328221935837014,
//                                    20.95029170238876
//                                ), 10.0f
//                            )
//                        ) // Warszawa
//
//                    } else {
//
//                        val hereX = location.latitude
//                        val hereY = location.longitude
//                        val here = LatLng(hereX, hereY)
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 10.0f))
//                    }
//
//                }
//            } else {
//                Toast.makeText(this, "Musisz włączyć usługę lokalizacji", Toast.LENGTH_SHORT).show()
//            }
//        }  else {
//            ActivityResultContracts.RequestPermission()
//        }
//    }

    private fun getNewLocation() {
//        locationRequest = LocationRequest.create().apply {
//            interval = 100
//            fastestInterval = 50
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            maxWaitTime = 100
//        }

//            locationRequest = LocationRequest()

    }

    private fun fetchLocation(){
        val task = fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
        }

        task.addOnSuccessListener {
            if(it != null) {
               val me = LatLng(it.latitude, it.longitude)
                mMap.addMarker(MarkerOptions().position(me).title("Twoja lokalizacja"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 10.0f))
            }
        }

        return
//
//        task.addOnSuccessListener {
//            if(it != null){
//                Toast.makeText(applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
//            }
//        }
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

//    private fun CheckPermission(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false
//    }

//    private fun ActivityResultContracts.RequestPermission() {
//        ActivityCompat.requestPermissions(
//            this@MapsActivity,
//            arrayOf(
//                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ), PERMISSION_ID
//        )
//    }
//
//    private fun isLocationEnabled(): Boolean {
//        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "Masz pozwolenie")
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera


        val rzeszow = LatLng(50.041843724550716, 21.997288949261403)

//        marker_rzeszow = mMap.addMarker(
//            MarkerOptions()
//                .position(rzeszow)
//                .title("Rzeszów")
//        )
//        marker_rzeszow?.tag = 0
//
//        mMap.setOnMarkerClickListener(this)

        sqliteHelper.selectMaps(googleMap)
        val warszawa = LatLng(52.328221935837014, 20.95029170238876)
//        mMap.addMarker(MarkerOptions().position(warszawa).title("Warszawa(mazowieckie)"))
//
//        val krakow = LatLng(50.11037700606547, 19.888640107936368)
//        mMap.addMarker(MarkerOptions().position(krakow).title("Kraków(małopolskie)"))
//
//        val lodz = LatLng(51.80253045749482, 19.39631174147701)
//        mMap.addMarker(MarkerOptions().position(lodz).title("Łódź(łódzkie)"))
//
//        val gdansk = LatLng(54.38946426564063, 18.67664115852412)
//        mMap.addMarker(MarkerOptions().position(gdansk).title("Gdańsk(pomorskie)"))
//
//        val wroclaw = LatLng(51.12610173656794, 17.05075319396246)
//        mMap.addMarker(MarkerOptions().position(wroclaw).title("Wrocław(dolnośląskie)"))
//
//        val czestochowa = LatLng(50.83652832579559, 19.210693922746632)
//        mMap.addMarker(MarkerOptions().position(czestochowa).title("Częstochowa(śląskie)"));
//
//        val miedzyzdroje = LatLng(53.941527437740305, 14.52717777495784)
//        mMap.addMarker(
//            MarkerOptions().position(miedzyzdroje).title("Miedzyzdroje(zachodniopomorskie)")
//        )
//
//        val wieliczka = LatLng(49.98353146914269, 20.054300653564418)
//        mMap.addMarker(
//            MarkerOptions().position(wieliczka).title("Kopalnia soli Wieliczka(małopolskie)")
//        )
//
//        val polanczyk = LatLng(49.36974426727602, 22.422793194320214)
//        mMap.addMarker(MarkerOptions().position(polanczyk).title("Polańczyk(podkarpackie)"))


//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rzeszow, 10.0f))


    }
    private fun GoogleMap.setOnMarkerClickListener(mapsActivity: MapsActivity) {

    }
}


