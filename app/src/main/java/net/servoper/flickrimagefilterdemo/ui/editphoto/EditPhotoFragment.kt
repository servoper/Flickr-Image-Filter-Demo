package net.servoper.flickrimagefilterdemo.ui.editphoto

import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.servoper.flickrimagefilterdemo.R
import net.servoper.flickrimagefilterdemo.databinding.FragmentEditPhotoBinding
import java.lang.Exception
import android.content.Intent
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.squareup.picasso.Target


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditPhotoFragment : Fragment() {

    private var _binding: FragmentEditPhotoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: EditPhotoFragmentArgs by navArgs()

    private val model: EditPhotoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentEditPhotoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_share -> {
                startSharing()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSharing() {
        Picasso.get()
            .load(args.imageUrl)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bitmap?.let {
                        model.prepareImageForSharing(
                            it,
                            binding.imageFilterView.contrast,
                            binding.imageFilterView.saturation
                        )
                    }
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage()

        attachFiltersListeners()

        model.sharingFileLiveData.observe(viewLifecycleOwner, {
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/jpeg"
            share.putExtra(Intent.EXTRA_STREAM, it)
            startActivity(Intent.createChooser(share, getString(R.string.share_conten_title)))
        })

        model.errorLiveData.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun attachFiltersListeners() {
        binding.saturationSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageFilterView.saturation = progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.contrastSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageFilterView.contrast = progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadImage() {
        Picasso.get()
            .load(args.imageUrl)
            .into(binding.imageFilterView, object : Callback {
                override fun onError(e: Exception?) {}

                override fun onSuccess() {
                    binding.contrastSeekBar.progress =
                        (binding.imageFilterView.contrast * 100).toInt()

                    binding.saturationSeekBar.progress =
                        (binding.imageFilterView.saturation * 100).toInt()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}