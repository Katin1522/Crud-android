package RecyclerViewHelpers

import Modelo.Conexion
import Modelo.dataClassMusica
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import katherine.ceron.crudkatherine1b.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adaptador (var Datos:List<dataClassMusica> ): RecyclerView.Adapter<ViewHolder>(){

    //Funcion para que cuando afrefue datos se actualice la lista automaticamente

    fun actualizarListado(nuevaLista: List<dataClassMusica>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    ///// FUNCION todo:Eliminar datos

    fun eliminarDatos(nombreCancion: String, position: Int){
        //Eliminarlo de la lista
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        //Eliminarlo de la base de datos
        GlobalScope.launch(Dispatchers.IO){
            //1-Crep un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2-Creo una variable que contenga un PrepareStatemet
            val deleteCancion = objConexion?.prepareStatement("delete tbMusica where nombreCancion = ?")!!
            deleteCancion.setString(1,nombreCancion)
            deleteCancion.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

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

        //Todo: clic al icono de borrar
        holder.imhBorrar.setOnClickListener {
            //Creo la alerta para confirmar la eliminacion
            //1- Invoco el contexto
            val context = holder.itemView.context

            //2- Creo la alerta [Usando los tres pasos]
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")
            builder.setMessage("¿Estas seguro que quieres borrar?")

            builder.setPositiveButton("Si"){ dialog, wich ->
                eliminarDatos(item.NombreCancion, position)
            }
            builder.setNegativeButton("No") { dialog, wich ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }





    }

    

}