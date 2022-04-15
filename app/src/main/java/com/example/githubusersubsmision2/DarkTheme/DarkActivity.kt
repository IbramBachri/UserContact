package com.example.githubusersubsmision2.DarkTheme


import android.os.Bundle

import android.view.MenuItem
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import com.example.githubusersubsmision2.databinding.ActivityDarkBinding

class DarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkBinding
    private val viewModel by viewModels<DarkViewModel>{
        DarkViewModel.Factory(SettingPreferences(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTheme().observe(this){
            if (it){
                binding.switchTheme.text = "Dark Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                binding.switchTheme.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
                binding.switchTheme.isChecked = it
            }
        binding.switchTheme.setOnCheckedChangeListener{_, isChacked ->
            viewModel.saveThemeSetting(isChacked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // destroy()Activity
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

