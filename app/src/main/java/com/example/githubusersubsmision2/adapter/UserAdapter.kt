package com.example.githubusersubsmision2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubusersubsmision2.databinding.ItemUserBinding
import com.example.githubusersubsmision2.datajson.ResponseGithubUser


class UserAdapter (private val data : MutableList<ResponseGithubUser.Item> = mutableListOf(),
private val setonlistener : (ResponseGithubUser.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun DataSet(data: MutableList<ResponseGithubUser.Item>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    class UserViewHolder(private val bindingitem: ItemUserBinding) :
        RecyclerView.ViewHolder(bindingitem.root) {
        fun bind(itemm : ResponseGithubUser.Item){
        bindingitem.imgItemPhoto.load(itemm.avatar_url){
            transformations(CircleCropTransformation())
        }
         bindingitem.tvItemName.text= itemm.login
            bindingitem.tvItemId.text= itemm.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false ))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            setonlistener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}