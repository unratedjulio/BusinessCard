package com.punkmic.businesscard.data

import com.punkmic.businesscard.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BusinessCardRepository @Inject constructor(
    private val dao: BusinessCardDao, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun insert(businessCard: BusinessCard) = runBlocking {
        launch(ioDispatcher) {
            dao.insert(businessCard)
        }
    }

    fun getAll() = dao.getAll()

    fun delete(card: BusinessCard) = runBlocking {
        launch(ioDispatcher) {
            dao.delete(card)
        }
    }
}