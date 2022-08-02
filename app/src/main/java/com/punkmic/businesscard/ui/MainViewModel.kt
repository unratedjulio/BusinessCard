package com.punkmic.businesscard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.punkmic.businesscard.data.BusinessCard
import com.punkmic.businesscard.data.BusinessCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val businessCardRepository: BusinessCardRepository) :
    ViewModel() {

    fun insert(businessCard: BusinessCard) {
        businessCardRepository.insert(businessCard)
    }

    fun getAll(): LiveData<List<BusinessCard>> {
        return businessCardRepository.getAll().asLiveData()
    }

    fun delete(card: BusinessCard) {
        businessCardRepository.delete(card)
    }

}