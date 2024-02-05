package umc.com.mobile.project.ui.career

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import umc.com.mobile.project.databinding.FragmentUploadBottomBinding
import umc.com.mobile.project.ui.career.viewmodel.CareerAddActivityViewModel
import umc.com.mobile.project.ui.career.viewmodel.CareerEditActivityViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class UploadBottomFragment(context: Context, private val viewModelType: Int) : BottomSheetDialogFragment() {
    private var _binding: FragmentUploadBottomBinding? = null
    private val addViewModel: CareerAddActivityViewModel by activityViewModels()
    private val editViewModel: CareerEditActivityViewModel by activityViewModels()
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
                            val selectedImageFile = createImageFile(imageUri)
                            addViewModel.addImageFile(selectedImageFile)
                        }
                    } else {
                        val imageUri = data?.data
                        if (imageUri != null) {
                            val selectedImageFile = createImageFile(imageUri)
                            addViewModel.addImageFile(selectedImageFile)
                        }
                    }
                }
            }
        }
    }

    private fun createImageFile(uri: Uri): File {
        val fileName = "img_" + System.currentTimeMillis() + ".jpg"
        val directory = requireContext().getExternalFilesDir(null)
        val file = File(directory, fileName)

        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream: OutputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return file
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}