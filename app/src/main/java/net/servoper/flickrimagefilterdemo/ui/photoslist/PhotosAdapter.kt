package net.servoper.flickrimagefilterdemo.ui.photoslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.servoper.flickrimagefilterdemo.R
import net.servoper.flickrimagefilterdemo.data.FlickrImageUrlBuilder
import net.servoper.flickrimagefilterdemo.data.model.Photo
import net.servoper.flickrimagefilterdemo.databinding.ItemPhotoBinding

class PhotosAdapter(val data: ArrayList<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = data[position]
        Picasso.get()
            .load(
                FlickrImageUrlBuilder.getUrl(
                    holder.binding.root.context, photo.farmId,
                    photo.serverId, photo.id.toString(), photo.secret
                )
            )
            //.placeholder(R.drawable.user_placeholder)
            //.error(R.drawable.user_placeholder_error)
            .into(holder.binding.photo)
    }

    override fun getItemCount(): Int = data.size
}