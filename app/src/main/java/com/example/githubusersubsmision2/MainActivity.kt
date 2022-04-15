package com.example.githubusersubsmision2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubsmision2.DarkTheme.DarkActivity
import com.example.githubusersubsmision2.DarkTheme.SettingPreferences
import com.example.githubusersubsmision2.ResultCurotine.ResultVM
import com.example.githubusersubsmision2.adapter.UserAdapter
import com.example.githubusersubsmision2.databinding.ActivityMainBinding
import com.example.githubusersubsmision2.datajson.ResponseGithubUser
import com.example.githubusersubsmision2.favorite.FavoriteActivity
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { userdetail ->
            Intent(this, DeatailUserActivity::class.java).apply {
                putExtra("item", userdetail)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<ViewModelMain> {
        ViewModelMain.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
        //searchView
        binding.SearchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getusser(query.toString())
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean = false

        })

        viewModel.ResultGitHub.observe(this) {
            when (it) {
                is ResultVM.Succes<*> -> {
                    adapter.DataSet(it.data as MutableList<ResponseGithubUser.Item>)
                }
                is ResultVM.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultVM.Loading -> {
                    binding.progressBar.isVisible = it.isloading
                }
            }
        }


        viewModel.getusser()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_bar -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.seting_btnde   -> {
                Intent(this, DarkActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}