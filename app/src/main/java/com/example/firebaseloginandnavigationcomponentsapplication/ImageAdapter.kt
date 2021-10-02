package com.example.firebaseloginandnavigationcomponentsapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter (var images:List<String>):RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_custom,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        var url = images[position]

        Glide.with(holder.itemView)
            .load(url)
            .into(holder.iv_image)

    }

    override fun getItemCount(): Int {
        return images.size
    }
}

class ImageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    lateinit var iv_image:ImageView
    init {

        iv_image =itemView.findViewById(R.id.item_custom_iv)
    }

}