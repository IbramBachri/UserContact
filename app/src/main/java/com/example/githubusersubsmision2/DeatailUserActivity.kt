package com.example.githubusersubsmision2

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubusersubsmision2.DarkTheme.DarkActivity
import com.example.githubusersubsmision2.ResultCurotine.ResultVM
import com.example.githubusersubsmision2.adapter.VPAdapter
import com.example.githubusersubsmision2.databinding.ActivityDeatailUserBinding

import com.example.githubusersubsmision2.datajson.ResponeDetailUser
import com.example.githubusersubsmision2.datajson.ResponseGithubUser
import com.example.githubusersubsmision2.favorite.ConfiqDatabase
import com.example.githubusersubsmision2.fragmentfollowerwing.FollowsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DeatailUserActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityDeatailUserBinding
    private val  viewModel by viewModels<DeatailVM>(){
        DeatailVM.Factory(ConfiqDatabase(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ResponseGithubUser.Item>("item")
        val username = item?.login?:""
        val company = intent.getStringExtra("company")?: ""
        val bio = intent.getStringExtra("bio")?: ""
        val location = intent.getStringExtra("lokasi")?: ""
        val login =  intent.getStringExtra("login")?: ""
        val follwer = intent.getStringExtra("follower")?:""
        val following =  intent.getStringExtra("Following")?: ""
        val repos =  intent.getStringExtra("repos")?: ""

        viewModel.ResultDeatilU.observe(this){
            when(it){
                is ResultVM.Succes<*> -> {
                    val user = it.data as ResponeDetailUser
                    binding.tvImgDtl.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }

                    binding.namaDtl.text = user.name
                    binding.idTv.text = user.login
                    binding.tvCompany.text=user.company
                    binding.tvBio.text= user.bio as CharSequence?
                    binding.tvLocation.text=user.location
                    binding.tvFollower.text= user.followers.toString()
                    binding.tvFollowing.text= user.following.toString()
                    binding.tvRepos.text= user.public_repos.toString()

                }
                is ResultVM.Error ->{
                    Toast.makeText(this,it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultVM.Loading ->{
                    binding.progressBardetail.isVisible = it.isloading
                }
            }
        }
        viewModel.getDetailusser(username, login, company,bio,location,follwer,following, repos,  )

        val fragment = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf(
            getString(R.string.followers),
            getString(R.string.following),
        )
        val adapter = VPAdapter(this,fragment)
        binding.vpFollows.adapter = adapter

        TabLayoutMediator(binding.tab, binding.vpFollows){ tab, posisi ->
            tab.text = titleFragment[posisi]

        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0){
                    viewModel.getFollowers(username)
                }else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        viewModel.getFollowers(username)

        viewModel.ResultSuccsesFavorite.observe(this){
            binding.favoriteFt.changeIconColor(R.color.Merah)
        }
        viewModel.ResultDeleteFavorite.observe(this){
            binding.favoriteFt.changeIconColor(R.color.white)
        }
        binding.favoriteFt.setOnClickListener{
            viewModel.saveUser(item)
        }
        viewModel.findFavorite(item?.id ?: 0){
            binding.favoriteFt.changeIconColor(R.color.Merah)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.seting_btn -> {
                Intent(this, DarkActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
    imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}