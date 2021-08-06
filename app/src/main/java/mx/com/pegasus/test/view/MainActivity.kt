package mx.com.pegasus.test.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import mx.com.pegasus.test.R
import mx.com.pegasus.test.view.adapter.PlacesAdapter
import mx.com.pegasus.test.view.fragments.MapFragment
import mx.com.pegasus.test.viewmodel.LoginViewModel
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
                override fun onPlaceClick(
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
            viewModelSignUp.saveLocalData(flowTo)
        })
        viewModelSignUp.visitedAll.observe(this, { flowTo ->
            if(flowTo) {
                mRecyclerView.visibility = View.GONE
                card_visited_all.visibility = View.VISIBLE
            }
            else {
                mRecyclerView.visibility = View.VISIBLE
                card_visited_all.visibility = View.GONE
            }
        })

        search_item.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mAdapter.filter.filter(search_item.text)
            }

        })
        hello_user.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml("<p><b>Hola Ignacio,</b> buenos dias!</p>", Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml("<p><b>Hola Ignacio,</b> buenos dias!</p>")
        }

        viewModelSignUp.errorMessage.observe(this, { flowTo ->
            Log.e("LOCALDATA","-->" + flowTo)
            if(flowTo){
                viewModelSignUp.getLocalData()
            }else{
                viewModelSignUp.getPlacesFromWeb()
            }
        })
        viewModelSignUp.validateLocalData()

    }

    fun setUpRecyclerView(){
        mRecyclerView = findViewById(R.id.list_places) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        viewModelSignUp.validateLocalData()
    }
}