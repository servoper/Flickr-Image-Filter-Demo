package net.servoper.flickrimagefilterdemo.ui.editphoto

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import net.servoper.flickrimagefilterdemo.R
import net.servoper.flickrimagefilterdemo.data.FlickrImageUrlBuilder
import net.servoper.flickrimagefilterdemo.databinding.FragmentEditPhotoBinding
import java.lang.Exception


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EditPhotoFragment : Fragment() {

    private var _binding: FragmentEditPhotoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: EditPhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentEditPhotoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_share -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadImage()

        attachFiltersListeners()
    }

    private fun attachFiltersListeners() {
        binding.brightnessSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageFilterView.brightness = progress.toFloat() / 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

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
                    binding.brightnessSeekBar.progress =
                        (binding.imageFilterView.brightness * 100).toInt()

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