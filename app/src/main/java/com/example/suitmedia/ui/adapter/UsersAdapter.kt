package com.example.suitmedia.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmedia.databinding.ItemUserBinding
import com.example.suitmedia.model.Data
import com.example.suitmedia.ui.MainActivity
import com.example.suitmedia.ui.SecondScreenActivity

class UsersAdapter(private val activity: MainActivity) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(){

    private var list = ArrayList<Data>()
    inner class UsersViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data){
            binding.tvItemEmail.text = data.email
            val name = data.first_name + data.last_name
            binding.tvItemName.text = name
            Glide.with(itemView)
                .load(data.avatar)
                .into(binding.imgItemAvatar)
            itemView.setOnClickListener{
                val intent = Intent(it.context, SecondScreenActivity::class.java)
                val item = Data(
                    data.avatar,
                    data.email,
                    data.first_name,
                    data.id,
                    data.last_name
                )
                intent.putExtra(EXTRA_ITEM, item)
                activity.setResult(RESULT_CODE,intent)
                activity.finish()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(items: ArrayList<Data>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }

    companion object {
        const val EXTRA_ITEM= "extra_item"
        const val RESULT_CODE = 110
    }
}