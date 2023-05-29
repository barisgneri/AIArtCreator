package com.barisguneri.aiartcreator.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentInAppBinding

class InAppFragment : Fragment() {
    private lateinit var binding: FragmentInAppBinding

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        isSelected()

        if (binding.plan.isSelected != binding.plan.isSelected){
            binding.plan.setBackgroundResource(R.drawable.in_app_state)
        }

        binding.closeBtn.setOnClickListener {
            editor.putString("isPurchase","Satın Almadı")
            editor.putString("OnBoardingPreferences","SatınAlınmadı").apply()
            findNavController().navigate(R.id.action_inAppFragment_to_homeFragment)
        }

        binding.startBtn.setOnClickListener { startBtn() }


    }

    private fun isSelected(){
        val ideas2 = listOf(binding.plan, binding.plan2,)

        val selectIdea = { selected: View ->
            ideas2.forEach { plans ->
                if (plans.id != selected.id) {
                    plans.isSelected = false

                } else {
                    selected.isSelected = true
                    selected.setBackgroundResource(R.drawable.in_app_state)
                }
            }
        }

        ideas2.forEach { idea ->
            idea.setOnClickListener {
                selectIdea(idea)
            }
        }
    }

    private fun startBtn(){

        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val plans = arrayListOf(
            binding.plan,
            binding.plan2
        )

        for ((index, plan) in plans.withIndex()){
            if (plan.isSelected){
                sharedPreferences.edit().putString("OnBoardingPreferences","SatınAlındı").apply()
                println("Seçtiğiniz Plan: ${plan.text}")
                editor.putString("isPurchase","Satın Aldı")
                findNavController().navigate(R.id.action_inAppFragment_to_homeFragment)
            }
        }
    }

}