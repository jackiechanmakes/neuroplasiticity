package com.example.neuroplasticity.ui.exercise


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExerciseViewModel: ViewModel() {

    // List of words previously used in game
    private var usedWordsList: MutableList<String> = mutableListOf()
    // List of words used for each level
    var levelWordsList: MutableList<String> = mutableListOf()
    private lateinit var tempWord: String

    private val _level = MutableLiveData<Int>(0)
    val level: LiveData<Int> = _level

    private val _numTriesLeft = MutableLiveData<Int>(0)
    val numTriesLeft: LiveData<Int> = _numTriesLeft

    private val _stimuliText = MutableLiveData<String>("")
    val stimuliText: LiveData<String> = _stimuliText

    private val _maxNumWordsRecalled = MutableLiveData<Int>(0)
    val maxNumWordsRecalled: LiveData<Int> = _maxNumWordsRecalled

    init {
        _level.value = 2
        _numTriesLeft.value = 2
        _maxNumWordsRecalled.value = 3
    }

    fun setLevel(updatedLevel: Int) {
        _level.value = updatedLevel
    }

    fun setNumTriesLeft(numTries: Int) {
        _numTriesLeft.value = numTries
    }

    fun setMaxNumWordsRecalled(numWords: Int) {
        _maxNumWordsRecalled.value = numWords
    }

    /*
     * Updates tempWord and currentWord with the next word.
     */
    private fun getNextWord() {
        tempWord = allWordsList.shuffled().first()

        // Chooses another word if word has already been played before
        if (usedWordsList.contains(tempWord)) {
            getNextWord()
        } else {
            usedWordsList.add(tempWord)
            levelWordsList.add(tempWord)
        }
    }

    /*
     * Sets up next level
     */
    fun setWordsNextLevel() {
        levelWordsList.clear()

        for (i in 1.._level.value!!) {
            getNextWord()
        }

        setStimuliText()
    }

    private fun setStimuliText() {
        val separator = " "
        _stimuliText.value = levelWordsList.joinToString(separator)
    }

    /*
   * Re-initializes the game data to restart the game.
   */
    fun reinitializeData() {
        usedWordsList.clear()
        levelWordsList.clear()
        _stimuliText.value = ""
    }
}