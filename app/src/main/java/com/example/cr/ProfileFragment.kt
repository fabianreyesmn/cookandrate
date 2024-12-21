package com.example.cr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import android.util.Log

class ProfileFragment : Fragment() {
    private lateinit var profileImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var userTypeTextView: TextView
    private lateinit var biographyTextView: TextView
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize UI components
        profileImage = view.findViewById(R.id.profileImageView)
        nameTextView = view.findViewById(R.id.nameTextView)
        userTypeTextView = view.findViewById(R.id.userTypeTextView)
        biographyTextView = view.findViewById(R.id.biographyTextView)

        // Configure TabLayout and ViewPager
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager1)

        val adapter = ViewPagerAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Publicaciones"
                1 -> "Galería de fotos"
                else -> null
            }
        }.attach()

        // Observe the current user and update UI
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                // Set user's full name
                nameTextView.text = it.Nombre

                // Set user type
                userTypeTextView.text = it.TipoUsuario

                // Set biography
                biographyTextView.text = it.Biografia

                // Load profile image
                val imageUrl = userViewModel.getCurrentUserImageUrl()
                Log.d("ProfileFragment", "Image URL: $imageUrl")

                Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.cocinero)
                    .error(R.drawable.cocinero)
                    .circleCrop()
                    .into(profileImage)
            }
        }

        return view
    }
}