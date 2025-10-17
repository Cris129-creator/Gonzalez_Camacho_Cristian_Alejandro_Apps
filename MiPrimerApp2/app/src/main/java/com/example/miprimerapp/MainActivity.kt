package com.example.miprimerapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.stream.DoubleStream.builder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val video = findViewById<VideoView>(R.id.maria1)
        val  uri = Uri.parse(
            "android.resource://" + packageName + "/" + R.raw.themarias
        )
        video.setVideoURI(uri)

        video.setOnPreparedListener { mp ->  mp.start()
        video.requestFocus()

        }
        val button = findViewById<Button>(R.id.Accionbtn)
        val imageView = findViewById<ImageView>(R.id.imageView)

        val builder= AlertDialog.Builder(this)
        builder.setTitle("Mensaje Importate")
        builder.setPositiveButton("Aceptar") {dialog, _ ->
            dialog.dismiss()
        }


        button.setOnClickListener {

           val dialog: AlertDialog = builder.create()
            dialog.show()

            if (imageView.visibility == View.VISIBLE) {
                imageView.visibility = View.GONE
                Toast.makeText(this, "Hola! Cris imagen oculta", Toast.LENGTH_SHORT).show()
                button.text = "Mostrar imagen"
            } else {
                imageView.visibility = View.VISIBLE
                Toast.makeText(this, "Hola! Cris imagen visible" , Toast.LENGTH_SHORT).show()
              //  builder().setMessage("imagen visible");
                button.text = "Ocultar imagen"
            }
        }
}
    }
