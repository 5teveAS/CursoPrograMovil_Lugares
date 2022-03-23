package com.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lugares.data.LugarDao
import com.lugares.model.Lugar
import com.lugares.repository.LugarRepository

class LugarViewModel(application: Application)
    : AndroidViewModel(application) {

    val getAllData : MutableLiveData<List<Lugar>>

    private val repository: LugarRepository = LugarRepository(LugarDao())
    init {
        //val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
        // repository = LugarRepository(lugarDao)
        getAllData = repository.getAllData
    }
    suspend fun addLugar(lugar: Lugar) {
        //viewModelScope.launch(Dispatchers.IO) {repository.addLugar(lugar)}
        repository.addLugar(lugar)
    }
    suspend fun updateLugar(lugar: Lugar) {
        //viewModelScope.launch(Dispatchers.IO) {repository.updateLugar(lugar)}
        repository.addLugar(lugar)
    }
    suspend fun deleteLugar(lugar: Lugar){
        //viewModelScope.launch(Dispatchers.IO) {repository.deleteLugar(lugar)}
        repository.deleteLugar(lugar)
    }

}