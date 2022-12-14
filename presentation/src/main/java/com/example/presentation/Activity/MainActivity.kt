package com.example.presentation.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.Fragment.CategoryBtnFragment
import com.example.presentation.Fragment.SavedFragment
import com.example.presentation.Fragment.TopNewsFragment
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMainBinding

    private val topNewsFragment by lazy { TopNewsFragment() }
    private val categoryFragment by lazy { CategoryBtnFragment() }
    private val savedFragment by lazy { SavedFragment() }

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initNavigationBar()
    }

    private fun initNavigationBar() {

         navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController

        mBinding.bottomNav.setupWithNavController(navController)


    }


}