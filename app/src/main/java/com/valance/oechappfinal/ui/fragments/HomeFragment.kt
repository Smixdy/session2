package com.valance.oechappfinal.ui.fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.valance.oechappfinal.R
import com.valance.oechappfinal.databinding.HomeFragmentBinding

class HomeFragment: Fragment() {

    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        loadFragment(HomeFragment())
    }


    private fun loadFragment(fragment: Fragment) {
        if (fragmentsWithBottomNav.contains(fragment::class.java)) {
            showBottomNav()
        } else {
            hideBottomNav()
        }
    }

    private fun showBottomNav() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.visibility = View.GONE
    }

    private val fragmentsWithBottomNav = listOf(
        HomeFragment::class.java,
    )

    private fun setupListeners() {
        val relativeLayout1 = binding.relativeLayout
        val relativeLayout2 = binding.relativeLayout2
        val relativeLayout3 = binding.relativeLayout3
        val relativeLayout4 = binding.relativeLayout4

        val backgroundColor = requireContext().getColor(R.color.blue)
        val textColor = requireContext().getColor(R.color.white)
        val imageColor = requireContext().getColor(R.color.white)

        val clickListener = View.OnClickListener { view ->
            resetBackgroundAndTextColor()
            when (view.id) {
                R.id.relativeLayout -> {
                    val radius = resources.getDimensionPixelSize(R.dimen.radius_8dp).toFloat()
                    val backgroundDrawable = GradientDrawable().apply {
                        cornerRadius = radius
                        setColor(backgroundColor)
                    }
                    relativeLayout1.background = backgroundDrawable
                    binding.name.setTextColor(textColor)
                    binding.description.setTextColor(textColor)
                    binding.men.setColorFilter(imageColor)
                }
                R.id.relativeLayout2 -> {
                    val radius = resources.getDimensionPixelSize(R.dimen.radius_8dp).toFloat()
                    val backgroundDrawable = GradientDrawable().apply {
                        cornerRadius = radius
                        setColor(backgroundColor)
                    }
                    relativeLayout2.background = backgroundDrawable
                    binding.name1.setTextColor(textColor)
                    binding.description1.setTextColor(textColor)
                    binding.men1.setColorFilter(imageColor)
                }
                R.id.relativeLayout3 -> {
                    val radius = resources.getDimensionPixelSize(R.dimen.radius_8dp).toFloat()
                    val backgroundDrawable = GradientDrawable().apply {
                        cornerRadius = radius
                        setColor(backgroundColor)
                    }
                    relativeLayout3.background = backgroundDrawable
                    binding.name2.setTextColor(textColor)
                    binding.description2.setTextColor(textColor)
                    binding.men2.setColorFilter(imageColor)
                }
                R.id.relativeLayout4 -> {
                    val radius = resources.getDimensionPixelSize(R.dimen.radius_8dp).toFloat()
                    val backgroundDrawable = GradientDrawable().apply {
                        cornerRadius = radius
                        setColor(backgroundColor)
                    }
                    relativeLayout4.background = backgroundDrawable
                    binding.name3.setTextColor(textColor)
                    binding.description3.setTextColor(textColor)
                    binding.men3.setColorFilter(imageColor)
                }
            }
        }
        relativeLayout1.setOnClickListener(clickListener)
        relativeLayout2.setOnClickListener(clickListener)
        relativeLayout3.setOnClickListener(clickListener)
        relativeLayout4.setOnClickListener(clickListener)

    }

    private fun resetBackgroundAndTextColor() {
        val textColor = requireContext().getColor(R.color.blue)
        val imageColor = requireContext().getColor(R.color.blue)

        binding.relativeLayout.setBackgroundResource(R.drawable.card_bg)
        binding.relativeLayout2.setBackgroundResource(R.drawable.card_bg)
        binding.relativeLayout3.setBackgroundResource(R.drawable.card_bg)
        binding.relativeLayout4.setBackgroundResource(R.drawable.card_bg)
        binding.name.setTextColor(textColor)
        binding.description.setTextColor(textColor)
        binding.men.setColorFilter(imageColor)
        binding.name1.setTextColor(textColor)
        binding.description1.setTextColor(textColor)
        binding.men1.setColorFilter(imageColor)
        binding.name2.setTextColor(textColor)
        binding.description2.setTextColor(textColor)
        binding.men2.setColorFilter(imageColor)
        binding.name3.setTextColor(textColor)
        binding.description3.setTextColor(textColor)
        binding.men3.setColorFilter(imageColor)
    }
}
