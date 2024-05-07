package katherine.ceron.crudkatherine1b

import Modelo.Conexion
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1-Mandar a llamar a todos los elementos

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtDuracion = findViewById<EditText>(R.id.txtDuracion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        //2-Programar el boton
        btnAgregar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1-Crear un objeto de la clase conexion

                val objConexion = Conexion().cadenaConexion()

                //2- CRear una variable que contenga PrepareStatement
                val addMusica = objConexion?.prepareStatement("insert into tbmusica values(?, ? , ?)")!!
                addMusica.setString(1, txtNombre.text.toString())
                addMusica.setInt(2,txtDuracion.text.toString().toInt())
                addMusica.setString(3,txtAutor.text.toString())
                addMusica.executeUpdate()
            }
        }
    }
}