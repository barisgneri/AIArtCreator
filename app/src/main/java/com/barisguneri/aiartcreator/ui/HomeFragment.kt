package com.barisguneri.aiartcreator.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.barisguneri.aiartcreator.model.Categories
import com.barisguneri.aiartcreator.adapter.CategoryRVAdapter
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var backPressedTwice = false
    private val doubleBackPressDuration = 2000 // 2 saniye
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val controlBeforeLogin = sharedPreferences.getString("OnBoardingPreferences","")

        setupBackPressedTwice()

        var categoryList = ArrayList<Categories>()
        categoryList.add(Categories("Surrealist", R.drawable.img_surrealist_category,"Surrealist"))
        categoryList.add(Categories("Steampunk", R.drawable.img_steampunk_category,"Steampunk"))
        categoryList.add(
            Categories("Rick and Morty",
                R.drawable.img_rick_category,"Rick and Morty")
        )
        categoryList.add(
            Categories("Renaissance Painting",
                R.drawable.img_renaisence_category,"Renaissance Painting")
        )
        categoryList.add(
            Categories("Portrait Photo",
                R.drawable.img_portrait_category,"Portrait Photo")
        )
        categoryList.add(Categories("Pixelart", R.drawable.img_pixelart_category,"Pixelart"))
        categoryList.add(
            Categories("Pencil Drawing",
                R.drawable.img_pencil_category,"Pencil Drawing")
        )
        categoryList.add(Categories("Pastel", R.drawable.img_pastel_category,"Pastel"))
        categoryList.add(Categories("Oil Painting", R.drawable.img_oil_category,"Oil Painting"))
        categoryList.add(Categories("Mystical", R.drawable.img_mystical_category,"Mystical"))
        categoryList.add(
            Categories("Macrorealistic",
                R.drawable.img_macrorealist_category,"Macrorealistic")
        )
        categoryList.add(Categories("Lowpoly", R.drawable.img_lowpoly_category,"Lowpoly"))
        categoryList.add(Categories("Longshot", R.drawable.img_langshot_category,"Longshot"))
        categoryList.add(
            Categories("Gustavecourbet",
                R.drawable.img_gustavocourbet_category,"Gustavecourbet")
        )
        categoryList.add(
            Categories("Digital Painting",
                R.drawable.img_digital_category,"Digital Painting")
        )
        categoryList.add(Categories("Cyberpunk", R.drawable.img_cyberpunk_category,"Cyberpunk"))
        categoryList.add(Categories("Cinematic", R.drawable.img_cinematic_categories,"Cinematic"))
        categoryList.add(Categories("Anime", R.drawable.img_animation_categories,"Anime"))
        categoryList.add(Categories("Animation", R.drawable.img_cyberpunk_category,"Animation"))
        categoryList.add(Categories("3D Render", R.drawable.img_3d_categories,"3D Render"))
        categoryList.add(
            Categories("Watercolor Painting",
                R.drawable.img_watercolor_categories,"Watercolor Painting")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val categoryItem = CategoryRVAdapter(categoryList)
        binding.recyclerView.adapter = categoryItem

        val exIdea = arguments?.getString("exIdea")
        println(exIdea)

        if (exIdea != null) {
            val editableText: Editable = Editable.Factory.getInstance().newEditable(exIdea)
            binding.promptEditText.setText(exIdea)
        }



// Aldığınız veriyi kullanın.


        binding.selectExBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_exampleIdesFragment)
        }

        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.favoritesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoritesFragment)
        }

        binding.createBtn.setOnClickListener {
            if (controlBeforeLogin.equals("SatınAlındı")){
                //Toast.makeText(requireContext(), "Satın Alınmış", Toast.LENGTH_SHORT).show()
                if (binding.promptEditText.text.isNotEmpty() && categoryItem.getSelectedCategoryName() != null) {
                    val bundle = Bundle()
                    val prompt =
                        "${binding.promptEditText.text} in ${categoryItem.getSelectedCategoryName()}"
                    bundle.putString("prompt", "$prompt")
                    findNavController().navigate(
                        R.id.action_homeFragment_to_loadingFragment,
                        bundle
                    )
                }else{
                    Toast.makeText(requireContext(), "Prompt or categories is not empty", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Abonelik Almalısınız!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun setupBackPressedTwice() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                if (backPressedTwice) {
                    requireActivity().finish() // Uygulamadan çıkış
                } else {
                    backPressedTwice = true
                    Toast.makeText(
                        requireContext(),
                        "Çıkış Yapmak İçin Çift Tıklayın.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Zamanlayıcıyı başlat
                    Handler(Looper.getMainLooper()).postDelayed({
                        backPressedTwice = false
                    }, doubleBackPressDuration.toLong())
                }
                true
            } else {
                false
            }
        }
    }
}