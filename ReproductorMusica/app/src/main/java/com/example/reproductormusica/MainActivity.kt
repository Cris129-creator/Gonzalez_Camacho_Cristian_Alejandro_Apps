package com.example.reproductormusica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val usuario="Cris"
        val contra="crisUchiha"

        val edTUsuario: EditText = findViewById(R.id.edTUsuario)
        val edTContra: EditText = findViewById(R.id.edTContra)
        val btnAcceso: Button = findViewById(R.id.btnAcceso)

        btnAcceso.setOnClickListener {

            val  usuarioIng = edTUsuario.text.toString()
            val contraIng = edTContra.text.toString()


            if (usuarioIng == usuario && contraIng == contra){

                val builder= AlertDialog.Builder(this)
                builder.setTitle("Ingresaste correctamente, Bienvenido cris")
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                    val intent: Intent = Intent(this, BibliotecaActivity:: class.java)
                    startActivity(intent)
                }
                builder.show()

            }else{

                val builder= AlertDialog.Builder(this)
                builder.setTitle("Error al iniciar sesion, verifica tus datos")
                builder.setPositiveButton("Aceptar") {dialog, _ ->
                    dialog.dismiss()

                }
                builder.show()
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}