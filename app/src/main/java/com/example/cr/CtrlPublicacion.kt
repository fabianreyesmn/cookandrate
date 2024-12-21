package com.example.cr

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class CtrlPublicacion : LinearLayout {
    constructor(context: Context?) : super(context){
        inicializar()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        inicializar()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inicializar()
    }

    fun inicializar(){
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.ctrl_publicacion, this, true)

        val recyclerViewIng = findViewById<RecyclerView>(R.id.listaIngredientes)
        recyclerViewIng.layoutManager = LinearLayoutManager(context)
        val recyclerViewPasos = findViewById<RecyclerView>(R.id.listaElaboracion)
        recyclerViewPasos.layoutManager = LinearLayoutManager(context)

        val itemsIng = listOf("Pollo cocido", "Cilantro", "Ajonjolí", "Cacahuate")
        recyclerViewIng.adapter = AdapterIngredientes(itemsIng)

        val itemsPasos = listOf("Ajo", "Tomatillo verde", "Ajonjolí", "Cacahuate")
        recyclerViewPasos.adapter = AdapterIngredientes(itemsPasos)

        // Botones para interacción
        val meEncanta = findViewById<Button>(R.id.meEncanta)
        val meGusta = findViewById<Button>(R.id.meGusta)
        val noMeGusta = findViewById<Button>(R.id.noMeGusta)
        val rate = findViewById<Button>(R.id.rate)

        // Configurar listeners para cambiar background
        meEncanta.setOnClickListener {
            meEncanta.setBackgroundResource(R.drawable.baseline_favorite_24)
        }

        meGusta.setOnClickListener {
            meGusta.setBackgroundResource(R.drawable.baseline_thumb_up_alt_24)
        }

        noMeGusta.setOnClickListener {
            noMeGusta.setBackgroundResource(R.drawable.baseline_thumb_down_alt_24)
        }

        rate.setOnClickListener {
            rate.setBackgroundResource(R.drawable.baseline_gold_star_24)
        }
    }
}