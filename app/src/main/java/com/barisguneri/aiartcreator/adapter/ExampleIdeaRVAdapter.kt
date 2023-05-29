package com.barisguneri.aiartcreator.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barisguneri.aiartcreator.model.ExampleIdea
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.ExideaItemBinding

class ExampleIdeaRVAdapter(val ideaList: ArrayList<ExampleIdea>) :
    RecyclerView.Adapter<ExampleIdeaRVAdapter.IdeaHolder>() {
    private var selectedPosition = -1
    var secilenisim:String=""
    class IdeaHolder(val binding: ExideaItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeaHolder {
        val binding = ExideaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IdeaHolder(binding)
    }

    override fun onBindViewHolder(holder: IdeaHolder, position: Int) {
        val currentPosition = position
        holder.binding.btn3.text = ideaList[currentPosition].idea
        holder.binding.btn3.setBackgroundResource(if (currentPosition == selectedPosition) R.drawable.idea_big_btn_active else R.drawable.idea_big_btn)
        holder.binding.btn3.setTextColor(if (currentPosition == selectedPosition) Color.WHITE else Color.BLACK)

        holder.binding.btn3.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            secilenisim=ideaList[position].idea
            selectedPosition = currentPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

    }


    override fun getItemCount(): Int {
        return ideaList.size
    }

    fun getSelectedIdea(): String? {
        if (selectedPosition != -1) {
            Log.e("aasssdd",ideaList[selectedPosition].idea)
            return ideaList[selectedPosition].idea
        } else {
            Log.e("aasssdd","asdasd")
            return null
        }
    }

}