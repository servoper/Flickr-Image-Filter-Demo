package net.servoper.flickrimagefilterdemo.ui.photoslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.servoper.flickrimagefilterdemo.data.FlickrImageUrlBuilder
import net.servoper.flickrimagefilterdemo.data.model.Photo
import net.servoper.flickrimagefilterdemo.databinding.ItemPhotoBinding
import java.text.SimpleDateFormat
import java.util.*

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
                    photo.serverId, photo.id, photo.secret
                )
            )
            .into(holder.binding.photo)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = photo.dateAdded.toLong() * 1000

        holder.binding.dateAdded.text = SimpleDateFormat.getDateInstance().format(calendar.time)
        holder.binding.ownersName.text = photo.ownerName
        holder.binding.title.text = photo.title
    }

    override fun getItemCount(): Int = data.size
}