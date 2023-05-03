package com.example.advicesapp.search.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.advicesapp.R

class SearchAdapter(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<SearchViewHolder>(), Mapper.Unit<List<AdviceUi>> {

    private val list = mutableListOf<AdviceUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cards_search, parent, false),
        clickListener
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(list[position])

    override fun map(source: List<AdviceUi>) {
        val diff = DiffUtilCallback(list, source)
        val result = DiffUtil.calculateDiff(diff)
        list.clear()
        list.addAll(source)
        result.dispatchUpdatesTo(this)
    }
}

class SearchViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {

    private val number = itemView.findViewById<TextView>(R.id.id_cards)
    private val title = itemView.findViewById<TextView>(R.id.text_title_cards)
    private val subTitle = itemView.findViewById<TextView>(R.id.text_cards)
    private val favoriteImage = itemView.findViewById<ImageButton>(R.id.bookmark_card)
    private val mapper = ListItemUi(number, title, subTitle, favoriteImage)

    fun bind(model: AdviceUi) {
        model.map(mapper)
        favoriteImage.setOnClickListener {
            clickListener.click(model)
        }
    }

}

interface ClickListener {
    fun click(item: AdviceUi)
}

class DiffUtilCallback(
    private val oldList: List<AdviceUi>,
    private val newList: List<AdviceUi>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].map(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].equals(newList[newItemPosition])
}