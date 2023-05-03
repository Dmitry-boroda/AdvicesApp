package com.example.advicesapp.search.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.advicesapp.R
import com.example.advicesapp.core.presentation.BaseFragment
import com.example.advicesapp.databinding.SearchFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment : BaseFragment<SearchViewModel>() {

    override val viewModelClass = SearchViewModel::class.java
    override val layoutId = R.layout.search_fragment

    lateinit var binding: SearchFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SearchFragmentBinding.inflate(layoutInflater)

        val inputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        val inputEditText = view.findViewById<TextInputEditText>(R.id.textInputEditText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.searchRecycleView)
        val adapter = SearchAdapter(object : ClickListener {
            override fun click(item: AdviceUi) = viewModel.changeFavorite(item)
        })

        recyclerView.adapter = adapter

        binding.randomButton.setOnClickListener {
            viewModel.randomAdvice()
        }
        inputEditText.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.advices(binding.textInputEditText.text.toString())
                return@OnKeyListener true
            }
            false
        })
        viewModel.observe(this) {
            it.show(binding, adapter)
        }
    }
}
