package com.example.neuroplasticity

import android.graphics.Point
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.Button
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neuroplasticity.ui.exercise.ExerciseViewModel
import android.view.Menu

class ExerciseTrainingFragment : Fragment() {
    private val viewModel: ExerciseViewModel by activityViewModels()
    private lateinit var binding: com.example.neuroplasticity.databinding.FragmentExerciseTrainingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.example.neuroplasticity.R.layout.fragment_exercise_training, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding?.apply {
            exerciseTrainingFragment = this@ExerciseTrainingFragment
        }

        binding.constraintLayout.removeView(binding.stimuli)
        startExercise()
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }*/

    private fun startExercise() {
        viewModel.setWordsNextLevel()
        displayStimuliText()
    }

    var buttonViewsList: MutableList<Button> = mutableListOf()

    fun displayStimuliButtons() {
        val numStimuliWords = viewModel.levelWordsList.size

        for (i in 0 until numStimuliWords) {
            val button = Button(context)
            button.text = viewModel.levelWordsList[i]
            button.doOnLayout{
                var buttonPosition: Point = getButtonPosition(button)
                button.x = buttonPosition.x.toFloat()
                button.y = buttonPosition.y.toFloat()
            }
            binding.constraintLayout.addView(button)
            buttonViewsList.add(button)
        }
    }

    private fun getButtonPosition(button: Button): Point {
        var isButtonPositionValid = false
        var buttonPosition: Point = Point(0,0)
        while (!isButtonPositionValid) {
            buttonPosition = generateButtonPosition(button)
            isButtonPositionValid = checkButtonPositionValidity(buttonPosition)
        }
        return buttonPosition
    }

    private fun generateButtonPosition(button: Button): Point {
        // Get screen width and height
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels

        val gapDistance = 10

        var buttonX = (0..(width - button.width)).random()
        var buttonY = (getActionBarHeight()..(height - button.height -
                getNavigationBarHeight() - getStatusBarHeight() - gapDistance)).random()

        return Point(buttonX, buttonY)
    }

    private fun checkButtonPositionValidity(buttonPosition: Point): Boolean {
        var isButtonPositionValid = true
        val gapDistance = 80
        for (i in 0 until buttonViewsList.size) {
            val minX = buttonViewsList[i].x - gapDistance
            val maxX = buttonViewsList[i].x + buttonViewsList[i].width
            val minY = buttonViewsList[i].y - gapDistance/2
            val maxY = buttonViewsList[i].y + buttonViewsList[i].height

            if ((buttonPosition.x > minX && buttonPosition.x < maxX)
                && (buttonPosition.y > minY && buttonPosition.y < maxY)) {
                isButtonPositionValid = false
            }
        }
        return isButtonPositionValid
    }

    fun checkButtonClicks() {
        var wordNum = 0
        var isLevelPassed = true

        for (i in 0 until buttonViewsList.size) {
            buttonViewsList[i].setOnClickListener {

                if (buttonViewsList[i].text.equals(viewModel.levelWordsList[wordNum])) {
                    binding.constraintLayout.removeView(buttonViewsList[i])
                    buttonViewsList[i].setOnClickListener(null)
                    wordNum++
                } else {
                    isLevelPassed = false
                }

                // When last button of level is clicked
                if (wordNum == viewModel.level.value) {
                    if (isLevelPassed) {
                        if (viewModel.level.value!! > viewModel.maxNumWordsRecalled.value!!) {
                            viewModel.setMaxNumWordsRecalled(viewModel.level.value!!)
                        }
                        viewModel.setLevel(viewModel.level.value?.plus(1)!!)
                    } else {
                        if (viewModel.level.value!! > 2) {
                            viewModel.setLevel(viewModel.level.value?.minus(1)!!)
                        }
                        viewModel.setNumTriesLeft(viewModel.numTriesLeft.value!!.minus(1))
                    }

                    if (viewModel.numTriesLeft.value!! <= 0 || viewModel.level.value!! > 10) {
                        findNavController().navigate(R.id.action_exerciseTrainingFragment_to_exerciseEndFragment)
                    } else {
                        startExercise()
                    }
                }
            }
        }
    }

    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun getActionBarHeight(): Int {

        var actionBarHeight = 0

        val styledAttributes =
            requireContext().theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        actionBarHeight = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()
        return actionBarHeight
    }

    fun getNavigationBarHeight(): Int {
        var navigationBarHeight = 0
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return navigationBarHeight
    }

    private fun clearButtonsFromView(numStimuliWords: Int, buttonViewsList: MutableList<Button>) {
        for (i in 0 until numStimuliWords) {
            binding.constraintLayout.removeView(buttonViewsList[i])
        }
    }

    private fun displayStimuliText() {
        binding.constraintLayout.addView(binding.stimuli)
        val timer = object: CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                binding.constraintLayout.removeView(binding.stimuli)
                displayStimuliButtons()
                checkButtonClicks()
            }
        }
        timer.start()
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartExercise() {
        viewModel.reinitializeData()
    }

    /*
     * Exits the exercise.
     */
    private fun exitExercise() {
        activity?.finish()
    }
}