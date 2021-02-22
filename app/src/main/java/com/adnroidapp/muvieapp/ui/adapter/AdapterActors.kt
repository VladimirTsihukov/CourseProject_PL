package com.adnroidapp.muvieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.muvieapp.ClassKey.BASE_URL_MOVIE_IMAGE
import com.adnroidapp.muvieapp.R
import com.adnroidapp.muvieapp.mvp.model.api.data.Cast
import com.adnroidapp.muvieapp.ui.image.GlideImageLoaderActor

class AdapterActors(val imageLoader: GlideImageLoaderActor) : RecyclerView.Adapter<AdapterActors.HolderActors>() {

    private var actors = listOf<Cast>()

    fun bindActors(newActor: List<Cast>) {
        actors = newActor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderActors {
        return HolderActors(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderActors, position: Int) {
        holder.onBindActor(actors[position])
    }

    override fun getItemCount(): Int = actors.size

    inner class HolderActors(item: View) : RecyclerView.ViewHolder(item) {
        private val imageActor: ImageView = item.findViewById(R.id.holder_actor_image)
        private val nameActors: TextView = item.findViewById(R.id.holder_actor_name)

        fun onBindActor(actor: Cast) {
            nameActors.text = actor.name
            imageActor.let {
                imageLoader.loadInto(BASE_URL_MOVIE_IMAGE + actor.profilePath, it)
            }
        }
    }
}
