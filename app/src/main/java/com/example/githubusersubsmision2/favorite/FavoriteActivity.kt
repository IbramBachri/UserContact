package com.example.githubusersubsmision2.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubsmision2.DarkTheme.DarkActivity
import com.example.githubusersubsmision2.DeatailUserActivity
import com.example.githubusersubsmision2.MainActivity
import com.example.githubusersubsmision2.R
import com.example.githubusersubsmision2.adapter.UserAdapter
import com.example.githubusersubsmision2.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val adapter by lazy {
        UserAdapter { userdetail ->
            Intent(this, DeatailUserActivity::class.java).apply {
                putExtra("item", userdetail)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<favoriteVM> {
        favoriteVM.Factory(ConfiqDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvFavo.layoutManager = LinearLayoutManager(this)
        binding.rvFavo.adapter = adapter

        viewModel.getUserFavorite().observe(this) {
            adapter.DataSet(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fav, menu)
        return super.onCreateOptionsMenu(menu)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.seting_fav -> {
                Intent(this, DarkActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}