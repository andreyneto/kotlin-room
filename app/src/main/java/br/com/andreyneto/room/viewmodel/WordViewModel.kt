package br.com.andreyneto.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.andreyneto.room.entities.Word
import br.com.andreyneto.room.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository =  WordRepository(application)

    val allWords = repository.allWords

    infix fun insert(word: Word) {
        repository insert word
    }
}