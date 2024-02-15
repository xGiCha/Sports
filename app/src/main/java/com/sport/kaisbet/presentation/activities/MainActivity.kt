package com.sport.kaisbet.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sport.kaisbet.R
import com.sport.kaisbet.presentation.fragments.SportsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, SportsFragment()).commit()
    }
}