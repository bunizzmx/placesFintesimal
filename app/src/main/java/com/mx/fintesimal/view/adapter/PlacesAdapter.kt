package com.mx.fintesimal.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mx.fintesimal.R
import com.mx.fintesimal.model.database.DbPlaces
import com.mx.fintesimal.view.onClickItem

class PlacesAdapter: RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {
    var superheros: MutableList<DbPlaces>  = ArrayList()
    lateinit var context: Context
    lateinit var listener : onClickItem
    fun RecyclerAdapter(superheros : MutableList<DbPlaces>, context: Context,listener:onClickItem){
        this.superheros = superheros
        this.context = context
        this.listener = listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = superheros.get(position)
        holder.bind(item, context,listener)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_place, parent, false))
    }
    override fun getItemCount(): Int {
        return superheros.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val superheroName = view.findViewById(R.id.suburb_label) as TextView
        val realName = view.findViewById(R.id.domicilio_label) as TextView
        val status_label = view.findViewById(R.id.status_label) as TextView
        fun bind(superhero:DbPlaces, context: Context,listener: onClickItem){
            superheroName.text = superhero.suburb
            realName.text = superhero.domicilio
            if(superhero.visited) {
                status_label.setTextColor(Color.parseColor("#32c2c2"))
                status_label.text = "Visitado"
            }
            else {
                status_label.setTextColor(Color.parseColor("#AAAAAA"))
                status_label.text = " Pendiente"
            }
            //   avatar.loadUrl(superhero.photo)
            itemView.setOnClickListener(View.OnClickListener { listener.error(superhero.domicilio,superhero.suburb,
                superhero.location!!.latitude.toLong(),
                superhero.location!!.longitude.toLong(),superhero.visited,
                superhero.id!!
            ) })
        }
        fun ImageView.loadUrl(url: String) {
          //  Picasso.with(context).load(url).into(this)
        }
    }
}