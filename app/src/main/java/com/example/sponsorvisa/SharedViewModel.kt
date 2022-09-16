package com.example.sponsorvisa

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sponsorvisa.model.Company
import com.example.sponsorvisa.repository.Repository

class SharedViewModel(app: Application) : AndroidViewModel(app) {


    private val repository: Repository
    val localData: LiveData<List<Company>> get() = _localData
    private var _localData = MutableLiveData<List<Company>>()

    init {
        repository = Repository(app)
        setvalue()
    }

    private fun setvalue() {
        _localData.postValue(repository.getLocalCompanies())
    }
}