package umc.com.mobile.project.ui.career

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentUploadBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddActivityViewModel
import java.io.File

class UploadBottomFragment (context: Context) : BottomSheetDialogFragment() {
    private var _binding: FragmentUploadBottomBinding? = null
    private val viewModel: CareerAddActivityViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBottomBinding.inflate(inflater, container, false)

        _binding!!.ivUploadBottomGalleryBox.setOnClickListener {
            openGallery()
        }

        _binding!!.ivUploadBottomClose.setOnClickListener {
            dialog!!.dismiss()
        }
        return binding.root
    }

    private val GALLERY = 1
    private val PICK_IMAGE_MULTIPLE = 2

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_MULTIPLE -> {
                    if (data?.clipData != null) {
                        val count = data.clipData!!.itemCount
                        for (i in 0 until count) {
                            val imageUri = data.clipData!!.getItemAt(i).uri
                            val selectedImageFile = File(getRealPathFromURI(imageUri))
                            viewModel.addImageFile(selectedImageFile)
                        }
                    } else {
                        val imageUri = data?.data
                        if (imageUri != null) {
                            val selectedImageFile = File(getRealPathFromURI(imageUri))
                            viewModel.addImageFile(selectedImageFile)
                        }
                    }
                }
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val idx = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            val path = it.getString(idx)
            it.close()
            return path
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
    /*private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY) {
            val selectedImageUri = data?.data
        }
    }*/
     */