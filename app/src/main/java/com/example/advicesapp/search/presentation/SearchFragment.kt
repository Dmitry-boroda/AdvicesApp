package com.example.advicesapp.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.advicesapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout)
        val inputEditText = view.findViewById<TextInputEditText>(R.id.textInputEditText)
        val randomButton = view.findViewById<Button>(R.id.random_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.searchRecycleView)
        val process = view.findViewById<ProgressBar>(R.id.progressBar)

        randomButton.setOnClickListener {
            viewModel.randomAdvice()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container)
    }
}
