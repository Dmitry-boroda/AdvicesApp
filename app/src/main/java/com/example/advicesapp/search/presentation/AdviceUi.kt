package com.example.advicesapp.search.presentation

import android.widget.ImageButton
import android.widget.TextView
import com.example.advicesapp.R

data class AdviceUi(
    private val id: Int,
    private val query: String,
    private val text: String,
    private val isFavorite: Boolean
) : Mapper<Boolean, AdviceUi> {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, query, text, isFavorite)

    interface Mapper<T> {
        fun map(id: Int, query: String, text: String, isFavorite: Boolean): T
    }

    override fun map(source: AdviceUi): Boolean = source.id == id
}

class ListItemUi(
    private val number: TextView,
    private val title: TextView,
    private val subTitle: TextView,
    private val favoriteImage: ImageButton,
) : AdviceUi.Mapper<Unit> {
    override fun map(id: Int, query: String, text: String, isFavorite: Boolean) {
        number.text = id.toString()
        title.text = query
        subTitle.text = text

        if (isFavorite)
            favoriteImage.setImageResource(R.drawable.outline_bookmark_24)
        else
            favoriteImage.setImageResource(R.drawable.outline_bookmark_border_24)
    }
}