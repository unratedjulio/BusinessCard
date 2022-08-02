package com.punkmic.businesscard.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.punkmic.businesscard.databinding.ActivityMainBinding
import com.punkmic.businesscard.util.Image
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy { BusinessCardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()
        getAllBusinessCard()
        insertListening()
    }

    private fun initAdapter() {
        binding.rvCards.adapter = adapter
    }

    private fun insertListening() {
        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }
        adapter.listenerShare = { card ->
            Image.share(this, card)
        }
        adapter.listenerDelete = { card ->
            viewModel.delete(card)
        }
    }

    private fun getAllBusinessCard() {
        viewModel.getAll().observe(this) { businessCards ->
            adapter.submitList(businessCards)
        }
    }
}