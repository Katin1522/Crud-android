package RecyclerViewHelpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import katherine.ceron.crudkatherine1b.R


class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val txtNombre: TextView = view.findViewById(R.id.txtCancion)



}