package com.adnroidapp.muvieapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.adnroidapp.muvieapp.model.api.data.Movie

class DiffUtilMovie(
    private val oldList: List<Movie>,
    private val newList: List<Movie>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].likeMovies == newList[newItemPosition].likeMovies
    }
}