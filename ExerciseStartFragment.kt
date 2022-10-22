package com.example.neuroplasticity

import android.graphics.Point
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neuroplasticity.ui.exercise.ExerciseViewModel

class ExerciseStartFragment: Fragment() {
    private val viewModel: ExerciseViewModel by activityViewModels()
    private lateinit var binding: com.example.neuroplasticity.databinding.FragmentExerciseStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.neuroplasticity.R.layout.fragment_exercise_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.startButton.setOnClickListener {
            viewModel.reinitializeData()
            findNavController().navigate(R.id.action_exerciseStartFragment_to_exerciseTrainingFragment)
        }
    }
}