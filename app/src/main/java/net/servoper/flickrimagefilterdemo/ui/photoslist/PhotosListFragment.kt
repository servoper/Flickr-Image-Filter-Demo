package net.servoper.flickrimagefilterdemo.ui.photoslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import net.servoper.flickrimagefilterdemo.R
import net.servoper.flickrimagefilterdemo.data.FlickrImageUrlBuilder
import net.servoper.flickrimagefilterdemo.data.model.Photo
import net.servoper.flickrimagefilterdemo.databinding.FragmentPhotosListBinding
import net.servoper.flickrimagefilterdemo.ui.custom.RecyclerItemClickListener

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PhotosListFragment : Fragment() {

    private lateinit var mAdapter: PhotosAdapter
    private var _binding: FragmentPhotosListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val model: PhotosViewModel by activityViewModels()

    private val mPhotos = ArrayList<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPhotosListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = PhotosAdapter(mPhotos)

        binding.potosRecyclerView.adapter = mAdapter

        model.requestPhotos()

        context?.let {
            binding.potosRecyclerView.addOnItemTouchListener(
                getRecyclerItemClickListener(it)
            )
        }

        model.photosLiveData.observe(viewLifecycleOwner, {
            mPhotos.addAll(it)
            mAdapter.notifyItemRangeInserted(0, it.size)
        })

        model.errorLiveData.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun getRecyclerItemClickListener(context: Context) = RecyclerItemClickListener(
        context,
        object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val photo = mPhotos[position]
                val action = PhotosListFragmentDirections.actionPhotosListToEditPhoto(
                    FlickrImageUrlBuilder.getUrl(
                        context, photo.farmId,
                        photo.serverId, photo.id, photo.secret
                    )
                )
                findNavController().navigate(action)
            }
        })

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}