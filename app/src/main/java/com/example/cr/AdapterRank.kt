package com.example.cr

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterRank(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2 // Número de pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAficionados()
            1 -> TabProfesionales()
            else -> throw IllegalStateException("Posición desconocida: $position")
        }
    }
}
