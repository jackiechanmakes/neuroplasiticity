package com.example.neuroplasticity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neuroplasticity.ui.exercise.ExerciseViewModel

class ExercisePausedFragment : Fragment() {
    private val viewModel: ExerciseViewModel by activityViewModels()
    private lateinit var binding: com.example.neuroplasticity.databinding.FragmentExercisePausedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.neuroplasticity.R.layout.fragment_exercise_paused, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding?.apply {
            exercisePausedFragment = this@ExercisePausedFragment
        }
    }

    /**
     * Navigate to the start screen.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_exercisePausedFragment_to_exerciseStartFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }
}