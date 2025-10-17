package com.example.logueocris

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class pantalla2 : AppCompatActivity() {

    val list = mutableListOf<CarouselItem>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla2)

        val carousel: ImageCarousel = findViewById(R.id.carousel)

        list.add(CarouselItem("https://i.pinimg.com/736x/c3/00/e8/c300e81ff562263465faabd6bfbb2e3f.jpg"))
        list.add(CarouselItem("https://i.pinimg.com/736x/1c/46/d8/1c46d84c17e9b328b1e2d1fcadd4b582.jpg"))
        list.add(CarouselItem("https://resizing.flixster.com/vYJ8RzJsgJ7mYQJIE_nrfNBWARc=/fit-in/705x460/v2/https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/1414707_v9_aa.jpg"))
        list.add(CarouselItem("https://itsoundsalternative.com/wp-content/uploads/2025/06/The-marias-1-compressed.jpg"))
        list.add(CarouselItem("https://i.pinimg.com/736x/85/62/17/856217d211b1388ff4b1c6fee23835e7.jpg"))
        list.add(CarouselItem("https://i.pinimg.com/736x/f8/12/59/f81259ac914bb31d50ded8d17256f6fe.jpg"))

        carousel.addData(list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}