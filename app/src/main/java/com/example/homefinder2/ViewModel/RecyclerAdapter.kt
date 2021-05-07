package com.example.homefinder2.ViewModel

import android.content.Context
import android.view.KeyCharacterMap.load

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

import com.example.homefinder2.Model.Property
import com.example.homefinder2.R

import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.lang.System.load

class RecyclerAdapter (private val context: Context, private val propertyList: MutableList<Property> ) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.house_pic)
        var price: TextView = itemView.findViewById(R.id.price)
        var des: TextView = itemView.findViewById(R.id.descript)
        var address: TextView = itemView.findViewById(R.id.address)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.property, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = propertyList.size


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = propertyList[position]
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://homefinder3.appspot.com/").child("Property Pics").child(currentItem.imageResourceId)
        holder.price.text = currentItem.price
        holder.des.text = currentItem.description
        holder.address.text = currentItem.address
        Glide.with(context).load(storageReference).into(holder.image)
    }
}




