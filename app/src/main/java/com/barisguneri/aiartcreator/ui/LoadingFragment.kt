package com.barisguneri.aiartcreator.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.viewmodel.AppViewModel
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentLoadingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import com.barisguneri.aiartcreator.model.Result

class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding
    private lateinit var appViewModel: AppViewModel
    private var fragmentManager = activity?.supportFragmentManager
    private val apiViewModel by viewModel<AppViewModel>()
    private var text = ""
    private var category = ""
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = Bundle()

        text = arguments?.getString("prompt").toString()
        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    apiViewModel.getTextToImage(text, category)
                    apiViewModel.text2imageState.collectLatest {
                        when (it) {
                            is Result.Idle -> {
                                // show loading indicator if needed
                            }
                            is Result.Success -> {
                                it.data?.let { it1 ->
                                    val stream = ByteArrayOutputStream()
                                    it1.compress(Bitmap.CompressFormat.PNG, 90, stream)
                                    val image = stream.toByteArray()
                                    bundle.putByteArray("image",image)
                                    bundle.putString("photoName",text)
                                    sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                                    val controlBeforeLogin = sharedPreferences.getString("OnBoardingPreferences","")

                                    if (controlBeforeLogin == "SatınAlındı"){
                                        findNavController().navigate(R.id.action_loadingFragment_to_resultFragment, bundle)
                                    }else {
                                        findNavController().navigate(R.id.action_loadingFragment_to_onboardingResultFragment, bundle)
                                    }

                                }
                            }
                            is Result.Error -> {
                                Toast.makeText(requireContext(),"Hata Oluştu", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loadingFragment_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }

}
