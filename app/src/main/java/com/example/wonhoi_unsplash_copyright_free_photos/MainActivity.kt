package com.example.wonhoi_unsplash_copyright_free_photos

import android.Manifest
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.wonhoi_unsplash_copyright_free_photos.data.Repository
import com.example.wonhoi_unsplash_copyright_free_photos.data.models.PhotoResponse
import com.example.wonhoi_unsplash_copyright_free_photos.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            fetchRandomPhotos()
        } else {
            requestWriteStoragePermission()
        }

        fetchRandomPhotos()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val writeExternalStoragePermissionGranted =
            requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (writeExternalStoragePermissionGranted) {
            fetchRandomPhotos()
        }
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = PhotoAdapter()
    }

    private fun bindViews() {
        binding.searchEditText.setOnEditorActionListener { editText, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // EditTextView clear
                currentFocus?.let { view ->
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)

                    view.clearFocus()
                }

                fetchRandomPhotos(editText.text.toString())
            }
            // Listener Consume OK
            true
        }

        binding.refreshLayout.setOnRefreshListener {
            fetchRandomPhotos(binding.searchEditText.text.toString())
        }

        (binding.recyclerView.adapter as? PhotoAdapter)?.onClickPhoto = { clickedPhoto ->
            showDownloadPhotoConfirmationDialog(clickedPhoto)
        }
    }

    private fun requestWriteStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
        )
    }

    private fun fetchRandomPhotos(query: String? = null) = scope.launch {
        try {
            Repository.getRandomPhotos(query)?.let { getRandomPhotos ->
                binding.errorDescriptionTextView.visibility = View.GONE
                (binding.recyclerView.adapter as? PhotoAdapter)?.apply {
                    this.photos = getRandomPhotos    // photos
                    notifyDataSetChanged()
                }
            }
            binding.recyclerView.visibility = View.VISIBLE
        } catch (exception: Exception) {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.errorDescriptionTextView.visibility = View.VISIBLE
        } finally {
            // fetch complete
            binding.shimmerLayout.visibility = View.GONE
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun showDownloadPhotoConfirmationDialog(clickedPhoto: PhotoResponse) {
        AlertDialog.Builder(this)
            .setMessage("Would you like to save the selected picture?")
            .setPositiveButton("Save") { dialog, _ ->
                downloadPhoto(clickedPhoto.urls?.full)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun downloadPhoto(clickedPhotoUrl: String?) {
        clickedPhotoUrl ?: return


        Glide.with(this)
            .asBitmap()
            .load(clickedPhotoUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(
                object : CustomTarget<Bitmap>(SIZE_ORIGINAL, SIZE_ORIGINAL) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // save
                        saveBitmapToMediaStore(resource)

                        val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)

                        val snackbar = Snackbar.make(
                            binding.root,
                            "Download Complete",
                            Snackbar.LENGTH_SHORT
                        )

                        if (wallpaperManager.isWallpaperSupported
                            && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                                    && wallpaperManager.isSetWallpaperAllowed)) {
                            snackbar.setAction("Save as Wallpaper") {
                                try {
                                    wallpaperManager.setBitmap(resource)
                                } catch (exception : Exception) {  // IOException
                                    Snackbar.make(binding.root, "Failed to save Wallpaper", Snackbar.LENGTH_SHORT)
                                }
                            }
                            snackbar.duration = Snackbar.LENGTH_INDEFINITE
                        }
                        snackbar.show()
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        Snackbar.make(
                            binding.root,
                            "Downloading...",
                            Snackbar.LENGTH_INDEFINITE
                        ).show()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Snackbar.make(
                            binding.root,
                            "Download failed",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) = Unit

                }
            )
    }

    private fun saveBitmapToMediaStore(resource: Bitmap) {
        val fileName = "${System.currentTimeMillis()}.jpg"
        val resolver = applicationContext.contentResolver

        val imageCollectionUri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {   // Android 10
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {   // Android 10
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        // imageUri is nullable
        val imageUri = resolver.insert(imageCollectionUri, imageDetails)

        imageUri ?: return
        // file write. openOutputStream is closeable
        resolver.openOutputStream(imageUri).use { outputStream ->
            resource.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {   // Android 10
            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, imageDetails, null, null)
        }
    }

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 101
    }


}




