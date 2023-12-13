package com.fetchrewards.ui

import com.fetchrewards.R
import com.fetchrewards.ui.fragments.MainFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance(), "TAG").commitNow()
    }
}