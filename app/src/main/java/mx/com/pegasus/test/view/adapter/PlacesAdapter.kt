package mx.com.pegasus.test.view.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import mx.com.pegasus.test.R
import mx.com.pegasus.test.model.database.DbPlaces
import mx.com.pegasus.test.view.onClickItem
import java.util.*
import kotlin.collections.ArrayList


class PlacesAdapter: RecyclerView.Adapter<PlacesAdapter.ViewHolder>(), Filterable {
    var superheros: MutableList<DbPlaces>  = ArrayList()
    var backupitems : MutableList<DbPlaces>  = ArrayList()
    lateinit var context: Context
    lateinit var listener : onClickItem
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                superheros.clear()
                superheros.addAll(backupitems)
                val charSearch = constraint.toString()
                    val resultList = ArrayList<DbPlaces>()
                    for (row in superheros) {
                        if (row.domicilio.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                superheros=resultList
                val filterResults = FilterResults()
                filterResults.values = superheros
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                superheros = results?.values as ArrayList<DbPlaces>
                notifyDataSetChanged()
            }

        }
    }


    fun RecyclerAdapter(superheros : MutableList<DbPlaces>, context: Context,listener:onClickItem){
        this.superheros = superheros
        this.backupitems.addAll(superheros)
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
        val point_item =view.findViewById(R.id.point_item) as CardView
        fun bind(superhero:DbPlaces, context: Context,listener: onClickItem){
            superheroName.text = superhero.suburb
            realName.text = superhero.domicilio
            if(superhero.visited) {
                status_label.setTextColor(Color.parseColor("#32c2c2"))
                status_label.text = "Visitado"
                point_item.setCardBackgroundColor(Color.parseColor("#32c2c2"))
            }
            else {
                point_item.setCardBackgroundColor(Color.parseColor("#aaaaaa"))
                status_label.setTextColor(Color.parseColor("#aaaaaa"))
                status_label.text = " Pendiente"
            }
            //   avatar.loadUrl(superhero.photo)
            itemView.setOnClickListener {
                if(superhero.id==null){
                    superhero.id =0L
                }
                listener.onPlaceClick(
                        superhero.domicilio, superhero.suburb,
                        superhero.latitude!!.toLong(),
                        superhero.longitude!!.toLong(), superhero.visited,
                        superhero.id!!
                )
            }
        }
        fun ImageView.loadUrl(url: String) {
          //  Picasso.with(context).load(url).into(this)
        }
    }
}