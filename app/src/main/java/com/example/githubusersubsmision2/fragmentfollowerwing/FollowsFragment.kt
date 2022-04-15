package com.example.githubusersubsmision2.fragmentfollowerwing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubsmision2.DeatailVM
import com.example.githubusersubsmision2.ResultCurotine.ResultVM
import com.example.githubusersubsmision2.adapter.UserAdapter
import com.example.githubusersubsmision2.databinding.FragmentFollowsBinding
import com.example.githubusersubsmision2.datajson.ResponseGithubUser

class FollowsFragment : Fragment() {

    private var bindingfollows : FragmentFollowsBinding? = null
    override fun onDestroyView() {
        super.onDestroyView()
        var _binding = null
    }
    private val adapter by lazy {
        UserAdapter{

        }
    }
    private val viewModel by activityViewModels<DeatailVM>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingfollows = FragmentFollowsBinding.inflate(layoutInflater)
        return bindingfollows?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingfollows?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }

        when(type){
            FOLLOWERS ->{
                viewModel.ResultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.ResultFollowing.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
       // viewModel.ResultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
        //viewModel.ResultFollowers.observe(viewLifecycleOwner, this::manageResultFollows)
        //Must be separate

    }

    private fun manageResultFollows(state : ResultVM){
        when(state){
            is ResultVM.Succes<*> -> {
                adapter.DataSet(state.data as MutableList<ResponseGithubUser.Item>)
            }
            is ResultVM.Error ->{
                Toast.makeText(requireActivity(),state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is ResultVM.Loading ->{
                bindingfollows?.progressBar3?.isVisible = state.isloading // ? beacause null
            }
        }

    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101
        fun newInstance(type: Int) =
            FollowsFragment()
                .apply {
                    this.type =type
                }
    }
}