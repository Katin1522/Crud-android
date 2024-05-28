package katherine.ceron.crudkatherine1b

import Modelo.Conexion
import Modelo.dataClassMusica
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val rcvMusica = findViewById<RecyclerView>(R.id.rcvMusica)
        //Asignarle un Layout al RecyclerView
        rcvMusica.layoutManager = LinearLayoutManager(this)

        /////////// TODO:mostrar datos

        fun mostraDatos(): List<dataClassMusica> {
            // 1- Crear un objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            //2- Creo un Statement
            val statemt = objConexion?.createStatement()
            val resultSet = statemt?.executeQuery("SELECT * FROM tbMusica")!!

            // Voy a guardar todo lo que me traiga el select
            val canciones = mutableListOf<dataClassMusica>()

            while (resultSet.next()) {
                val nombre = resultSet.getString("nombreCancion")
                val cancion = dataClassMusica(nombre)
                canciones.add(cancion)

            }
            return canciones
        }

        //Asigna el adapter al R ecycler View
        //Ejecutar la funcion para mostrar datos

        CoroutineScope(Dispatchers.IO).launch{
            //Creo una variable que ejecute la funcion de mostrar Datos
            val musicaDB = mostraDatos()
            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(musicaDB)
                rcvMusica.adapter = miAdaptador
            }
        }

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

                val nuevasCanciones = mostraDatos()
                withContext(Dispatchers.Main){
                    //Actualizo al adaptador con los datos nuevos
                    (rcvMusica.adapter as? Adaptador)?.actualizarListado(nuevasCanciones)


                    
                }
            }
        }
    }
}