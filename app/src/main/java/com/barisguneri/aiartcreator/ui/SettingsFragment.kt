package com.barisguneri.aiartcreator.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val controlBeforeLogin = sharedPreferences.getString("OnBoardingPreferences","")

        if (controlBeforeLogin.equals("SatınAlındı")){
            binding.tryPremiumLayout.visibility = View.GONE
        }else{
            binding.tryPremiumLayout.visibility = View.VISIBLE
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        binding.tryPremiumLayout.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_inAppFragment)
        }

        binding.privacyPolicy.setOnClickListener {
            getUrlFromIntent("https://www.neonapps.co/")
        }

        binding.termsLayout.setOnClickListener {
            getUrlFromIntent("https://www.neonapps.co/")
        }

        binding.restoreLayout.setOnClickListener {
            getUrlFromIntent("market://details?id=com.barisguneri.aiartcreator")
        }
        binding.rateUs.setOnClickListener {
            getUrlFromIntent("market://details?id=com.barisguneri.aiartcreator")
        }
        binding.feedbackLayout.setOnClickListener {
            getUrlFromIntent("mailto:baris@neonapps.co")
        }

    }

    fun getUrlFromIntent(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}