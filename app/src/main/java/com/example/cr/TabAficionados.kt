package com.example.cr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class TabAficionados : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_aficionados, container, false)
        recyclerView = view.findViewById(R.id.chefsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchAficionadosChefs()
        return view
    }

    private fun fetchAficionadosChefs() {
        apiService = RetrofitClient.getInstance().create(ApiService::class.java)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val chefsResponse = apiService.getAllChefs()
                val usersResponse = apiService.getAllUsers()

                val aficionadosChefs = chefsResponse.chefs
                    .filter { it.TipoChef == "Chef Aficionado" }
                    .mapNotNull { chef ->
                        val user = usersResponse.users.find { it.ID_User == chef.ID_User }
                        user?.let { ChefWithUserDetails(chef, it) }
                    }
                    .sortedByDescending { it.chef.RatingPromedio }

                recyclerView.adapter = AficionadoAdapter(aficionadosChefs)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}