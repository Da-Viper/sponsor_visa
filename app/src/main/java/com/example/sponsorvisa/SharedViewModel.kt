package com.example.sponsorvisa

import android.app.Application
import androidx.lifecycle.*
import com.example.sponsorvisa.data.CompanyRepositoryImpl
import com.example.sponsorvisa.data.local.Company
import dagger.hilt.EntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: CompanyRepositoryImpl,
    application: Application
) :
    AndroidViewModel(application) {


    val localData: LiveData<List<Company>> get() = _localData
    private var _localData = MutableLiveData<List<Company>>()

    init {
        setvalue()
    }

    private fun setvalue() {
        viewModelScope.launch(Dispatchers.IO) {
            _localData.postValue(repository.getCompanies())
        }
    }
}