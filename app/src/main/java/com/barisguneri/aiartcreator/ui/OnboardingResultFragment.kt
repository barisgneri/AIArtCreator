package com.barisguneri.aiartcreator.ui

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentOnboardingResultBinding

class OnboardingResultFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bitmap = arguments?.getByteArray("image")
        val drawable = BitmapDrawable(resources, BitmapFactory.decodeByteArray(bitmap, 0, bitmap?.size ?: 0))
        binding.constraintLayout.background = drawable

        binding.startExplo.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingResultFragment_to_inAppFragment)
        }
    }

}