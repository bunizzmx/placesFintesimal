package com.mx.fintesimal.view.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mx.fintesimal.R
import com.mx.fintesimal.databinding.FragmentMapBinding
import com.mx.fintesimal.model.database.DbPlaces
import com.mx.fintesimal.viewmodel.LoginViewModel
import com.mx.fintesimal.viewmodel.MapViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


internal class MapFragment:DaggerAppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private var lat:Long = 0L
    private var lon:Long = 0L
    private var domicilio:String=""
    private var suburb:String =""
    private var visited:Boolean=false
    private var id:Long=0L

    private var order_pos: LatLng? = null

    @Inject
    lateinit var viewModelSignUp: MapViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var binding: FragmentMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelSignUp = ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
             lat = bundle.getLong("LAT") // 1
             lon = bundle.getLong("LON") // 1
             id = bundle.getLong("ID") // 1
             suburb = bundle.getString("SUBURB").toString() // 1
            domicilio = bundle.getString("DOMICILIO").toString() // 1
            visited = bundle.getBoolean("VISITED")

        }
        Log.e("ID_MAPITEMS","-->" + id)
        if(visited) {
            binding.statusLabelMap.setTextColor(Color.parseColor("#3ac2c2"))
            binding.point.setBackgroundColor(Color.parseColor("#3ac2c2"))
        }
        else {
            binding.statusLabelMap.setTextColor(Color.parseColor("#aaa"))
            binding.point.setBackgroundColor(Color.parseColor("#aaa"))
        }

        binding.domicilioLabel.text = domicilio
        binding.suburbLabel.text = suburb
        binding.toPosition.setOnClickListener {
                val gmmIntentUri =
                    Uri.parse("google.navigation:q=" + lat + "," + lon)
                val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
        }
        binding.visitedPlace.setOnClickListener {
            viewModelSignUp.updatePlace(
                DbPlaces(
                    id, domicilio, suburb,
                    lat.toDouble(), lon.toDouble(), true, null
                )
            )
        }
        //status_label_map
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            mMap = googleMap

            // Add a marker in Sydney and move the camera
            val sydney = LatLng(lat.toDouble(), lon.toDouble())
            mMap.addMarker(
                MarkerOptions()
                    .position(sydney).icon(
                        BitmapDescriptorFactory.fromBitmap(toBipMap())
                    )
                    .title("Marker in Sydney")
            )
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        lat.toDouble(),
                        lon.toDouble()
                    ), 12.0f
                )
            )
        })
    }

    fun toBipMap():Bitmap{
        val bitmap: Bitmap
        val drawable = (applicationContext.getResources().getDrawable(R.drawable.ic_marker) )
        bitmap = Bitmap.createBitmap(
            drawable.getIntrinsicWidth(),
            drawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)

      return   bitmap;
    }

}