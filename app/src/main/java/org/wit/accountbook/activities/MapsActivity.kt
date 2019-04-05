package org.wit.accountbook.activities

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import org.wit.accountbook.R
import org.wit.accountbook.models.AccountBookModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener{

    private lateinit var map: GoogleMap
    var location = org.wit.accountbook.models.Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        location = intent.extras.getParcelable("location")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
    //drag the marker
    override fun onMarkerDragStart(marker: Marker) {
    }
    override fun onMarkerDrag(marker: Marker) {
    }
    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }


    //change the content of marker when we dragged the marker to a different place
    override fun onMarkerClick(marker: Marker):Boolean{
        val loc = LatLng(location.lat,location.lng)
        marker.setSnippet("GPS:"+loc.toString())
        return false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        // Add a marker in Sydney and move the camera
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Place")
            .snippet("GPS:"+loc.toString())
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,location.zoom))
        map.setOnMarkerDragListener(this)
        map.setOnMarkerClickListener(this)
    }


    //intercept the back button, and send it back to the parent activity
    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location",location)
        setResult(Activity.RESULT_OK,resultIntent)
        finish()
        super.onBackPressed()
    }
}
