package com.barisguneri.aiartcreator.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barisguneri.aiartcreator.model.Favorites
import com.barisguneri.aiartcreator.databinding.FavoritesItemBinding
import com.barisguneri.aiartcreator.db.RoomEntity

class FavoritesRVAdapter(private var data: List<RoomEntity>, var onClick : (Int)->Unit) :
    RecyclerView.Adapter<FavoritesRVAdapter.FavoritesHolder>() {

    class FavoritesHolder(val binding: FavoritesItemBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder {
        val binding = FavoritesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {

        val byteArray = data[position]
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeByteArray(byteArray.image, 0, byteArray.image.size, options)

        holder.binding.imgView.setImageBitmap(bitmap)
        holder.binding.favoriteName.text = data[position].name

        holder.itemView.setOnClickListener {
            onClick(position)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}