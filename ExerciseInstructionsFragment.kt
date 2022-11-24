package com.example.neuroplasticity

import android.graphics.Color.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neuroplasticity.ui.exercise.ExerciseViewModel


class ExerciseInstructionsFragment : Fragment() {
    private var brainButton: Button? = null
    private var healButton: Button? = null
    private var workButton: Button? = null

    private val viewModel: ExerciseViewModel by activityViewModels()
    private lateinit var binding: com.example.neuroplasticity.databinding.FragmentExerciseInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.neuroplasticity.R.layout.fragment_exercise_instructions, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.exerciseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding?.apply {
            exerciseInstructionsFragment = this@ExerciseInstructionsFragment
        }

        // Set up menu toolbar
        binding.toolbar.inflateMenu(R.menu.menu_toolbar)


        startAnimation()
    }

    /**
     * Navigate to the start screen.
     */
    fun goToStartScreen() {
        findNavController().navigate(R.id.action_exerciseInstructionsFragment_to_exerciseTrainingFragment)
    }

    private fun animation1Animator(): ViewPropertyAnimator? {
        return binding.instructionsText.animate().alpha(0F).setDuration(1000).setStartDelay(2000)
            .withEndAction(animation1EndAction)
    }


    private val animation1EndAction =
        Runnable {
            binding.stimuli.visibility = View.VISIBLE
            animation2Animator()!!.start()
        }

    private fun animation2Animator(): ViewPropertyAnimator? {
        binding.instructionsText.text = "The ordered list of words will disappear and reappear randomly placed on the screen."
        return binding.instructionsText.animate().alpha(1F).setDuration(1000).setStartDelay(2000)
            .withEndAction(animation2EndAction)
    }

    private val animation2EndAction =
        Runnable {
            binding.stimuli.visibility = View.GONE
            animation3Animator()!!.start()
        }

    private fun animation3Animator(): ViewPropertyAnimator? {

        return binding.instructionsText.animate().alpha(0F).setDuration(1000).setStartDelay(2000)
            .withEndAction(animation3EndAction)
    }

    private val animation3EndAction =
        Runnable {

            val dm = resources.displayMetrics
            val width = dm.widthPixels
            val height = dm.heightPixels

            brainButton = Button(context)
            healButton = Button(context)
            workButton = Button(context)

            brainButton!!.setTextColor(ContextCompat.getColor(requireActivity(), R.color.queen_blue))
            brainButton!!.textSize = 22F
            brainButton!!.isAllCaps = false
            brainButton!!.text = "brain"
            brainButton!!.setBackgroundResource(R.drawable.training_button);
            brainButton!!.x = (width / 2 ).toFloat()
            brainButton!!.y = (height / 2).toFloat()
            binding.constraintLayout.addView(brainButton)


            healButton!!.setTextColor(ContextCompat.getColor(requireActivity(), R.color.queen_blue))
            healButton!!.textSize = 22F
            healButton!!.isAllCaps = false
            healButton!!.text = "heal"
            healButton!!.setBackgroundResource(R.drawable.training_button);
            healButton!!.x = (width / 5).toFloat()
            healButton!!.y = ((height / 2) + (height / 3)).toFloat()
            binding.constraintLayout.addView(healButton)

            workButton!!.setTextColor(ContextCompat.getColor(requireActivity(), R.color.queen_blue))
            workButton!!.textSize = 22F
            workButton!!.isAllCaps = false
            workButton!!.text = "work"
            workButton!!.setBackgroundResource(R.drawable.training_button);
            workButton!!.x = (width / 7 ).toFloat()
            workButton!!.y = (height / 2 + height / 5).toFloat()
            binding.constraintLayout.addView(workButton)

            binding.instructionsText.text = "Find and click the words in order."
            animation4Animator()!!.start()
        }

    private fun animation4Animator(): ViewPropertyAnimator? {
        return binding.instructionsText.animate().alpha(1F).setDuration(1000).setStartDelay(2000)
            .withEndAction(animation4EndAction)
    }

    private val animation4EndAction =
        Runnable {
            animation5Animator()!!.start()
        }

    private fun animation5Animator(): ViewPropertyAnimator? {
        //brainButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
        brainButton!!.setBackgroundResource(R.drawable.training_button_click)
        return brainButton!!.animate().alpha(0F).setDuration(1000).setStartDelay(2000).withEndAction(animation5EndAction)
    }

    private val animation5EndAction =
        Runnable {
            //binding.healButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            healButton!!.setBackgroundResource(R.drawable.training_button_click)
            animation6Animator()!!.start()
        }

    private fun animation6Animator(): ViewPropertyAnimator? {
        return healButton!!.animate().alpha(0F).setDuration(1000).setStartDelay(2000).withEndAction(animation6EndAction)
    }

    private val animation6EndAction =
        Runnable {
           animation7Animator()!!.start()
        }

    private fun animation7Animator(): ViewPropertyAnimator? {
        //binding.workButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
        workButton!!.setBackgroundResource(R.drawable.training_button_click)
        return workButton!!.animate().alpha(0F).setDuration(1000).setStartDelay(2000).withEndAction(animation7EndAction)
    }

    private val animation7EndAction =
        Runnable {
            animation8Animator()!!.start()
        }

    private fun animation8Animator(): ViewPropertyAnimator? {
        return binding.instructionsText.animate().alpha(0F).setDuration(1000).setStartDelay(2000)
            .withEndAction(animation8EndAction)
    }

    private val animation8EndAction =
        Runnable {
            animation9Animator()!!.start()
        }

    private fun animation9Animator(): ViewPropertyAnimator? {
        binding.instructionsText.text = "You move on to the next level after clicking the current level's words in order on the first try. Each new level adds one word to the list of words. Try to memorize as many words as possible!"
        return binding.instructionsText.animate().alpha(1F).setDuration(1000).setStartDelay(2000).withEndAction(animation9EndAction)
    }

    private val animation9EndAction =
        Runnable {
            animation10Animator()!!.start()
        }

    private fun animation10Animator(): ViewPropertyAnimator? {
        return binding.instructionsText.animate().alpha(0F).setDuration(1000).setStartDelay(2000).withEndAction(animation10EndAction)
    }

    private val animation10EndAction =
        Runnable {
            animation11Animator()!!.start()
        }

    private fun animation11Animator(): ViewPropertyAnimator? {
        binding.startButton.visibility = View.VISIBLE
        return binding.startButton.animate().alpha(1F).setDuration(1000).setStartDelay(2000)
    }

    private fun startAnimation() {
        animation1Animator()!!.start()
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