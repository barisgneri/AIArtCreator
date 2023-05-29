package com.barisguneri.aiartcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentSelectIdeaBinding

class SelectIdeaFragment : Fragment() {

    private lateinit var binding: FragmentSelectIdeaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectIdeaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isSelected()
        binding.continueBtn.setOnClickListener {
            continueBtn()
        }
    }

    private fun isSelected() {
        val ideas2 = listOf(binding.idea1, binding.idea2, binding.idea3, binding.idea4, binding.idea5, binding.idea6, binding.idea7)

        val selectIdea = { selected: View ->
            ideas2.forEach { idea ->
                if (idea.id != selected.id) {
                    idea.isSelected = false

                } else {
                    selected.isSelected = true
                    selected.setBackgroundResource(R.drawable.selectidea_state)
                }
            }
        }

        ideas2.forEach { idea ->
            idea.setOnClickListener {
                selectIdea(idea)
            }
        }
    }

    private fun continueBtn() {
            val ideas = listOf(
                binding.idea1,
                binding.idea2,
                binding.idea3,
                binding.idea4,
                binding.idea5,
                binding.idea6,
                binding.idea7
            )

            for ((index, idea) in ideas.withIndex()) {
                if (idea.isSelected) {
                    // println("Selected idea: $index")
                    // println(idea.text.toString())
                    val bundle = Bundle()
                     bundle.putString("prompt",idea.text.toString())
                    findNavController().navigate(R.id.action_selectIdeaFragment_to_loadingFragment,bundle)
                    break // Exit loop once a selected idea is found
                }
            }
        }
    }


