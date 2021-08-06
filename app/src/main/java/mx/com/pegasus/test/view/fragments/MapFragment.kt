package mx.com.pegasus.test.view.fragments


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.fragment_map.*
import mx.com.pegasus.test.R
import mx.com.pegasus.test.model.database.DbPlaces
import mx.com.pegasus.test.viewmodel.MapViewModel
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)
        // set toolbar as support action bar

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
        Log.e("ID_MAPITEMS", "-->" + id)
        if(visited) {
            buttons_visit.visibility= View.GONE
            status_label_map.text = "Visitada"
            status_label_map.setTextColor(Color.parseColor("#3ac2c2"))
            point.setCardBackgroundColor(Color.parseColor("#3ac2c2"))
        }
        else {
            status_label_map.text = "Pendiente"
            buttons_visit.visibility= View.VISIBLE
            status_label_map.setTextColor(Color.parseColor("#aaaaaa"))
            point.setCardBackgroundColor(Color.parseColor("#aaaaaa"))
        }

        back_activity.setOnClickListener {
            onBackPressed()
        }
        domicilio_label.text = domicilio
        suburb_label.text = suburb
        to_position.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:"+lat+","+lon))
            startActivity(intent)
        }
        visited_place.setOnClickListener {
            Toast.makeText(applicationContext,domicilio + "" + " ahora esta visitado",Toast.LENGTH_LONG).show()
            viewModelSignUp.updatePlace(
                    DbPlaces(
                            id, domicilio, suburb,
                            lat.toDouble(), lon.toDouble(), true, null
                    )
            )
            visited = true
            buttons_visit.visibility= View.GONE
            status_label_map.text = "Visitada"
            status_label_map.setTextColor(Color.parseColor("#3ac2c2"))
            point.setCardBackgroundColor(Color.parseColor("#3ac2c2"))
            val sydney = LatLng(lat.toDouble(), lon.toDouble())
            if(mMap!=null) {
                mMap.addMarker(
                        MarkerOptions()
                                .position(sydney).icon(
                                        BitmapDescriptorFactory.fromBitmap(toBipMap())
                                )
                                .title(domicilio)
                )
            }
        }

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
                            .title(domicilio)
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
        var  drawable:Drawable
        if(visited)
            drawable = (applicationContext.getResources().getDrawable(R.drawable.ic_visited_marker) )
        else
            drawable = (applicationContext.getResources().getDrawable(R.drawable.ic_marker) )

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

    override fun onBackPressed() {
        super.onBackPressed()
    }

}