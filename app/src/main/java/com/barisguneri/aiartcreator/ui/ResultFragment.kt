package com.barisguneri.aiartcreator.ui

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barisguneri.aiartcreator.viewmodel.AppViewModel
import com.barisguneri.aiartcreator.R
import com.barisguneri.aiartcreator.db.RoomEntity
import com.barisguneri.aiartcreator.databinding.FragmentResultBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
   // private var imageID: Int? = null
    private var bitmap: ByteArray? = null
    private val apiViewModel by viewModel<AppViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), onBackPressedCallback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bitmap = arguments?.getByteArray("image")
        val photoName = arguments?.getString("photoName")
        var isFavorite = arguments?.getBoolean("isFavorite") ?: false // Default value is false

        val drawable =
            BitmapDrawable(resources,
                bitmap?.let { BitmapFactory.decodeByteArray(bitmap, 0, it.size) })

       if (isFavorite){
            binding.addFavorite.isSelected = false
       }else{
            binding.addFavorite.isSelected = true
        }

        // Set the click listener for the addFavorite button
        binding.addFavorite.setOnClickListener {
            if (!isFavorite) {
                viewLifecycleOwner.lifecycleScope.launch {
                    println(isFavorite)
                    isFavorite = true
                    binding.addFavorite.isSelected = false
                    saveImageToDao(drawable.bitmap,photoName.toString(),isFavorite)
                }
               // saveToRoom(drawable.bitmap, photoName.toString(), isFavorite)


                // Perform actions when the image is favored
            } else {
                // Remove from favorites
                viewLifecycleOwner.lifecycleScope.launch {
                    isFavorite = false
                    binding.addFavorite.isSelected = true
                    deleteImageToDao(drawable.bitmap)
                }

               // bitmap?.let { it -> deleteFromRoom(drawable.bitmap, photoName.toString(), it) }
                // Perform actions when the image is not favored
            }

            // Update the button state based on newFavoriteStatus
        }

        val drawableByte = BitmapDrawable(resources,
            bitmap?.let { BitmapFactory.decodeByteArray(bitmap, 0, it.size) })

        if (bitmap != null) {

            binding.imageResult.setImageDrawable(drawable)
            //saveToRoom(drawable.bitmap, photoName.toString(), isFavorite)
        }


        binding.textView.text = photoName


        binding.againCreate.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("prompt", photoName)
            findNavController().navigate(R.id.action_resultFragment_to_loadingFragment, bundle)
        }

        binding.backHome.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }

        binding.downloadImg.setOnClickListener {
            //val bitmap: Bitmap = drawableByte.bitmap // Kaydedilecek Bitmap'i buraya yerleştirin
            //saveBitmapToGallery(requireContext(), bitmap)
            saveImageToGallery(drawable.bitmap)
        }


    }
    private fun saveImageToGallery(bitmap: Bitmap, ) {
        val resolver = requireActivity().contentResolver
        val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val newImage = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpeg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val imageUri = resolver.insert(imageCollection, newImage)

        if (imageUri != null) {
            resolver.openOutputStream(imageUri).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    Toast.makeText(requireContext(),"İndirme Başarılı", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(),"İndirme Sırasında Hata Oldu!", Toast.LENGTH_SHORT).show()
        }
    }

    fun bitmapToByteArray (image: Bitmap) : ByteArray{
       val byteArrayOutputStream = ByteArrayOutputStream()
       image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
       return byteArrayOutputStream.toByteArray()
   }

    private suspend fun saveImageToDao(image: Bitmap, name: String, isFavorite: Boolean) {


            //handle room insert
            val image1 = RoomEntity(
                image = bitmapToByteArray(image),
                name = name,
                isFavorite = isFavorite,
            )
            viewLifecycleOwner.lifecycleScope.launch {
                apiViewModel.insertImage(image1)
            }
    }

    private suspend fun deleteImageToDao(image: Bitmap){
        viewLifecycleOwner.lifecycleScope.launch {
            apiViewModel.deleteImage(bitmapToByteArray(image))
        }
    }
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
        }
    }
/*
    fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
        // Kaydedilecek dosya adını oluşturun
        val fileName = "image_${System.currentTimeMillis()}.png"

        // Resim dosyasını kaydedeceğiniz dizini alın
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        // Resmi kaydetmek için dosya oluşturun
        val file = File(directory, fileName)

        // Bitmap'i JPEG formatında kaydetmek için OutputStream'i kullanın
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            Toast.makeText(requireContext(), "Kaydetme Başarılı", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Sorun Oluştu", Toast.LENGTH_LONG).show()
        } finally {
            outputStream?.close()
        }

        // Resmin galeriye eklenmesi için medya tarayıcısını güncelleyin
        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null) { _, _ ->
            // Tarayıcı güncellendiğinde gerçekleştirilecek işlemler burada yer alabilir
        }
    }

    private fun saveToRoom(bitmap: Bitmap, photoName: String, isFavorite: Boolean) {
        val db: FavoriteDatabase =
            Room.databaseBuilder(requireContext(), FavoriteDatabase::class.java, "images")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()
        val imageEntity = FavoriteImageEntity(imageByteArray, photoName, true)
        viewLifecycleOwner.lifecycleScope.launch {
            saveImageToDatabase(db, imageEntity)

        }
    }

    private fun deleteFromRoom(bitmap: Bitmap, photoName: String, byteArray: ByteArray) {
        val db: FavoriteDatabase =
            Room.databaseBuilder(requireContext(), FavoriteDatabase::class.java, "images")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()
        val imageEntity = FavoriteImageEntity(imageByteArray, photoName, true, )
        viewLifecycleOwner.lifecycleScope.launch {
            deleteImageFromDatabase(db, byteArray)
        }
    }





    // Veritabanına ekleme işlemini suspend fonksiyon içinde gerçekleştirin
    private suspend fun saveImageToDatabase(db: FavoriteDatabase, imageEntity: FavoriteImageEntity) {
        withContext(Dispatchers.IO) {
            db.favoriteImageDao().insertImage(imageEntity)
        }
    }

    // Veritabanına ekleme işlemini suspend fonksiyon içinde gerçekleştirin
    private suspend fun deleteImageFromDatabase(db: FavoriteDatabase, byteArray: ByteArray) {
        withContext(Dispatchers.IO) {
            db.favoriteImageDao().deleteById(byteArray)
        }
    }*/

}