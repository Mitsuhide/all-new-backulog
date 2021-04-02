package com.ttop.backulog.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ttop.backulog.R
import com.ttop.backulog.domain.Media
import com.ttop.backulog.domain.Status
import com.ttop.backulog.ui.MediaAdapter.*

class MediaAdapter(private val medias: List<Media>) : RecyclerView.Adapter<MediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MediaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.media_item_view,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = medias.size

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = medias[position]
        val view = holder.itemView
        with(view) {
            findViewById<TextView>(R.id.titreTextView).text = media.titre
            findViewById<ImageView>(R.id.statusImageView).setImageResource(
                when (media.etat) {
                    Status.PLANNED -> R.drawable.ic_bookmark
                    Status.ONGOING -> R.drawable.ic_play
                    Status.FINISHED -> R.drawable.ic_done
                }
            )
        }
    }

    class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}