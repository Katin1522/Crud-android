package RecyclerViewHelpers

import Modelo.dataClassMusica
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import katherine.ceron.crudkatherine1b.R

class Adaptador (var Datos: List<dataClassMusica> ): RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       //Conectar el RecyclerView con la card
        val vista  = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size
        //Devuelve la cantidad de valores que se muestren


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Poder darle clic a los elementos de la card
        val item = Datos[position]
        holder.txtNombre.text =item.NombreCancion
    }

}