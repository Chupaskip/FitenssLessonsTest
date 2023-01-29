package com.example.fitensslessonstest.ui

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fitensslessonstest.R
import com.example.fitensslessonstest.databinding.ActivityFitnessLessonsBinding

class FitnessLessonsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityFitnessLessonsBinding

    lateinit var viewModel: LessonsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessLessonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar_title)
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )
        )
        supportActionBar!!.elevation = 16F

        val viewModelProviderFactory = FitnessViewModelFactory(application)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LessonsViewModel::class.java)
    }


}