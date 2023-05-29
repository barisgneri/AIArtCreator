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
import com.barisguneri.aiartcreator.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val controlBeforeLogin = sharedPreferences.getString("OnBoardingPreferences","")
        println(controlBeforeLogin)
        if (controlBeforeLogin.equals("SatınAlınmadı")){
            findNavController().navigate(R.id.action_welcomeFragment_to_inAppFragment)
        }else if (controlBeforeLogin.equals("SatınAlındı")){
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }

        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_selectIdeaFragment)
        }
    }

}