package com.mx.fintesimal.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mx.fintesimal.R
import com.mx.fintesimal.view.adapter.PlacesAdapter
import com.mx.fintesimal.view.fragments.MapFragment
import com.mx.fintesimal.viewmodel.LoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelSignUp: LoginViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mRecyclerView : RecyclerView
    val mAdapter : PlacesAdapter = PlacesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView();

        viewModelSignUp = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModelSignUp.dogImages.observe(this, Observer { flowTo ->
            mAdapter.RecyclerAdapter(flowTo, this, object : onClickItem {
                override fun error(
                    domicilio: String,
                    suburb: String,
                    lat: Long,
                    lon: Long,
                    visited: Boolean,
                    id:Long
                ) {
                    val i = Intent(
                        applicationContext,
                        MapFragment::class.java
                    )
                    i.putExtra("LAT", lat)
                    i.putExtra("LON", lon)
                    i.putExtra("DOMICILIO", domicilio)
                    i.putExtra("SUBURB", suburb)
                    i.putExtra("VISITED",visited)
                    i.putExtra("ID",id)
                    startActivity(i)
                }

            })
            mRecyclerView.adapter = mAdapter
        })
        viewModelSignUp.getPlacesFromWeb();
    }

    fun setUpRecyclerView(){
        mRecyclerView = findViewById(R.id.list_places) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}