package com.barisguneri.aiartcreator.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barisguneri.aiartcreator.model.Categories
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.CategoryItemBinding

class CategoryRVAdapter(val categoryList: ArrayList<Categories>) : RecyclerView.Adapter<CategoryRVAdapter.CategoryHolder>() {

    private var selectedPosition = -1

    class CategoryHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val currentPosition = position


        holder.binding.nameTextView.text = categoryList[currentPosition].name
        holder.binding.useBtn.setBackgroundResource(if (currentPosition == selectedPosition) R.drawable.category_use_false_btn else R.drawable.category_use_true_btn)
        holder.binding.useBtn.setTextColor(if (currentPosition == selectedPosition) Color.WHITE else Color.BLACK)

        holder.binding.useBtn.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = currentPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        holder.binding.imageViewCategory.setImageResource(categoryList[currentPosition].img)
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun getSelectedCategoryName(): String? {
        if (selectedPosition != -1 && selectedPosition < categoryList.size) {
            return categoryList[selectedPosition].category
        }
        return null
    }

}
