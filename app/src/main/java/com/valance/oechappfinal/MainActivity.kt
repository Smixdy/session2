package com.valance.oechappfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.valance.oechappfinal.databinding.ActivityMainBinding
import com.valance.oechappfinal.ui.fragments.HomeFragment
import com.valance.oechappfinal.ui.fragments.ProfileFragment
import com.valance.oechappfinal.ui.fragments.StartFragment
import io.github.jan.supabase.SupabaseClient

class MainActivity : AppCompatActivity() {
    private lateinit var supabaseClient: SupabaseClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.SplashScreen)
        setContentView(R.layout.activity_start)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        val myApplication = application as MyApp
        supabaseClient = myApplication.supabaseClient

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.wallet -> {
//                    loadFragment(ChatFragment())
                    true
                }

                R.id.track -> {
//                    loadFragment(SettingFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
        loadFragment(StartFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.my_nav_host_fragment, fragment)
        transaction.commit()

        if (fragmentsWithBottomNav.contains(fragment::class.java)) {
            showBottomNav()
        } else {
            hideBottomNav()
        }
    }

    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }

    private val fragmentsWithBottomNav = listOf(
        HomeFragment::class.java,
    )

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}