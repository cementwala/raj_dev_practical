package com.app.practical.business_logic.list_screen

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.practical.R


import com.app.practical.callbacks.OnItemSelected
import com.app.practical.databinding.DeliveryListItemBinding
import com.app.practical.model.DeliveryItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class DeliveryAdapter( var deliveryList: ArrayList<DeliveryItem>, var onItemSelected: OnItemSelected):
    RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =  DataBindingUtil.inflate<DeliveryListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.delivery_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val deliveryItem = deliveryList[position]
        Glide
            .with(holder.binding.ivImage.context).applyDefaultRequestOptions(RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder))
            .load(deliveryItem.imageUrl)
            .into(holder.binding.ivImage)


        holder.binding.tvDescription.text=deliveryItem.description
        holder.itemView.setOnClickListener {
            onItemSelected.onSelected(position)
        }

    }

    override fun getItemCount(): Int {
      return  deliveryList.size
    }

    fun updateItems(deliveryList: ArrayList<DeliveryItem>){
        this.deliveryList=deliveryList
        notifyDataSetChanged()
    }

    class MyViewHolder(var binding: DeliveryListItemBinding): RecyclerView.ViewHolder(binding.root) {

    }
}