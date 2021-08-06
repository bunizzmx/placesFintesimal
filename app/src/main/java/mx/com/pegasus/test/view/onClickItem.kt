package mx.com.pegasus.test.view

interface onClickItem {
    fun onPlaceClick(domicilio:String, suburb:String, lat:Long, lon:Long, visited:Boolean, id:Long)
}