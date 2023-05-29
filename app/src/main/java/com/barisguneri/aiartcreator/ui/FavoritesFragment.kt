package com.barisguneri.aiartcreator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.adapter.FavoritesRVAdapter
import com.barisguneri.aiartcreator.databinding.FragmentFavoritesBinding
import com.barisguneri.aiartcreator.db.RoomEntity
import com.barisguneri.aiartcreator.viewmodel.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModel<AppViewModel>()
    private val imageList : MutableList<RoomEntity> = mutableListOf()
    private lateinit var adapter: FavoritesRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerView()

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_homeFragment)
        }


        /* favoritesList.add(Favorites(" Tropical Fish ", R.drawable.img_favorites_demo))
         favoritesList.add(Favorites("Distant Galaxy", R.drawable.img_favorites2))
         favoritesList.add(Favorites("Principal villainess", R.drawable.img_favorites3))
         favoritesList.add(Favorites("An old man", R.drawable.img_favorites4))
         favoritesList.add(Favorites("Birds on the sea ", R.drawable.img_favorites5))
         favoritesList.add(Favorites("Two Futuristic Towers", R.drawable.img_favorites6))
         favoritesList.add(Favorites("Distant Galaxy", R.drawable.img_favorites2))
         favoritesList.add(Favorites("Principal villainess", R.drawable.img_favorites3))
         favoritesList.add(Favorites("An old man", R.drawable.img_favorites4))
         favoritesList.add(Favorites("Birds on the sea ", R.drawable.img_favorites5))
         favoritesList.add(Favorites("Two Futuristic Towers", R.drawable.img_favorites6))*/


    }

    private fun createRecyclerView() {
        getAllData()
        imageList.clear()
        adapter = FavoritesRVAdapter(imageList){ position ->
            val a = imageList[position]

            // Öğe tıklandığında yapılacak işlemler
            val bundle = Bundle()
            //  bundle.putInt("id", a.id.toInt())
            // println(a.id)// Add the id to the bundle
            bundle.putByteArray("image", a.image)
            bundle.putString("photoName", a.name)
            bundle.putBoolean("isFavorite", a.isFavorite)
            println(a.isFavorite)
            findNavController().navigate(R.id.action_favoritesFragment_to_resultFragment,bundle)


        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

    }

    private fun getAllData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allImageList.collectLatest {
                imageList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

/*
    private fun fetchImagesFromDatabase() {
        viewLifecycleOwner.lifecycleScope.launch {
            val images = db.favoriteImageDao().getAllImages()
            favoritesList.clear()
            images.forEach {
                favoritesList.add(Favorites(it.id,it.name, it.image, it.isFavorite))
            }
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = FavoritesRVAdapter(favoritesList) { position ->
                val a = favoritesList[position]

                // Öğe tıklandığında yapılacak işlemler
                val bundle = Bundle()
              //  bundle.putInt("id", a.id.toInt())
               // println(a.id)// Add the id to the bundle
                bundle.putByteArray("image", a.image)
                bundle.putString("photoName", a.name)
                bundle.putBoolean("isFavorite", a.isFavorite)
                println(a.isFavorite)
                findNavController().navigate(R.id.action_favoritesFragment_to_resultFragment,bundle)


            }
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

*/
}

