package com.barisguneri.aiartcreator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.barisguneri.aiartcreator.model.ExampleIdea
import com.barisguneri.aiartcreator.adapter.ExampleIdeaRVAdapter
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.databinding.FragmentExampleIdesBinding

class ExampleIdesFragment : Fragment() {

    private lateinit var binding: FragmentExampleIdesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExampleIdesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ideaList = ArrayList<ExampleIdea>()
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_exampleIdesFragment_to_homeFragment)
        }

        ideaList.add(ExampleIdea("Dog"))
        ideaList.add(ExampleIdea("Cat"))
        ideaList.add(ExampleIdea("Lion"))
        ideaList.add(ExampleIdea("Elephant"))
        ideaList.add(ExampleIdea("Tiger"))
        ideaList.add(ExampleIdea("Giraffe"))
        ideaList.add(ExampleIdea("Monkey"))
        ideaList.add(ExampleIdea("Bear"))
        ideaList.add(ExampleIdea("Wolf"))
        ideaList.add(ExampleIdea("Fox"))
        ideaList.add(ExampleIdea("Rabbit"))
        ideaList.add(ExampleIdea("Dolphin"))
        ideaList.add(ExampleIdea("Penguin"))
        ideaList.add(ExampleIdea("Kangaroo"))
        ideaList.add(ExampleIdea("Hippopotamus"))
        ideaList.add(ExampleIdea("Crocodile"))
        ideaList.add(ExampleIdea("Snake"))
        ideaList.add(ExampleIdea("Horse"))
        ideaList.add(ExampleIdea("Owl"))
        ideaList.add(ExampleIdea("Butterfly"))


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val ideaItem = ExampleIdeaRVAdapter(ideaList)
        binding.recyclerView.adapter = ideaItem



        binding.applyExamp.setOnClickListener {
            val bundle = Bundle()
            val idea = ideaItem.secilenisim
            println("geldi:"+idea)
            bundle.putString("exIdea", idea)
         findNavController().navigate(R.id.action_exampleIdesFragment_to_homeFragment, bundle)

            // bundle.putString("idea", idea)
         //  println(bundle)
          //  findNavController().navigate(R.id.action_exampleIdesFragment_to_homeFragment,bundle)
        }
    }

}