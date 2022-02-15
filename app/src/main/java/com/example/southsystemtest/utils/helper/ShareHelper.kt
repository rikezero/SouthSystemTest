package com.example.southsystemtest.utils.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.example.southsystemtest.R
import com.example.southsystemtest.utils.Constants
import com.example.southsystemtest.utils.startActivitySlideTransition
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class ShareHelper : PermissionDispatcherHelper.OnPermissionResult {

    private var activity: Activity? = null
    private var fragment: androidx.fragment.app.Fragment? = null
    private val context: Context
    private val permissionHelper: PermissionDispatcherHelper
    private val receiptContainer: ViewGroup
    private var viewsToHide: List<View>? = null

    constructor(
        activity: Activity,
        receiptContainer: ViewGroup,
        viewsToHide: List<View>? = null
    ) {
        this.activity = activity
        this.receiptContainer = receiptContainer
        context = activity
        this.viewsToHide = viewsToHide
        permissionHelper = PermissionDispatcherHelper(
            activity = activity,
            requestCode = REQUEST_CODE,
            permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            onPermissionResult = this
        )
    }

    constructor(
        fragment: androidx.fragment.app.Fragment,
        receiptContainer: ViewGroup,
        viewsToHide: List<View>? = null
    ) {
        this.fragment = fragment
        activity = fragment.activity
        context = activity!!
        this.viewsToHide = viewsToHide
        this.receiptContainer = receiptContainer
        permissionHelper = PermissionDispatcherHelper(
            fragment = fragment,
            requestCode = 300,
            permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            onPermissionResult = this
        )
    }

    override fun onAllPermissionsGranted(requestCode: Int) {
        createReceiptAndShare()
    }

    override fun onPermissionsDenied(
        requestCode: Int,
        deniedPermissions: List<String>,
        deniedPermissionsWithNeverAskAgainOption: List<String>
    ) {
        print(requestCode)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat(Constants.SHARE_HELPER_DATE_FORMAT).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
    }

    private fun createReceiptAndShare() {
        setViewsVisibility(false)

        receiptContainer.post {

            val verticalPaddingSum = receiptContainer.paddingTop + receiptContainer.paddingBottom
            val bitmap = getBitmapFromView(
                view = receiptContainer,
                totalHeight = receiptContainer.getChildAt(0).measuredHeight + verticalPaddingSum,
                totalWidth = receiptContainer.measuredWidth
            )

            try {
                val file = createImageFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    PHOTO_QUALITY,
                    ostream
                )
                ostream.close()
                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/*"
                val photoURI = FileProvider.getUriForFile(context, Constants.AUTHORITY, file)
                share.putExtra(Intent.EXTRA_STREAM, photoURI)
                activity?.startActivitySlideTransition(Intent.createChooser(share, context.getString(
                    R.string.action_share_via)))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            setViewsVisibility(true)
        }
    }

    private fun setViewsVisibility(visible: Boolean) {
        viewsToHide?.let {
            it.forEach { view ->
                receiptContainer.findViewById<View>(view.id)?.visibility = if (visible) View.VISIBLE else View.GONE
            }
        }
    }

    private fun getBitmapFromView(
        view: View,
        totalHeight: Int,
        totalWidth: Int
    ): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(
            totalWidth,
            totalHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    fun shareContent() {
        permissionHelper.dispatchPermissions()
    }

    fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionHelper.onRequestPermissionsResult(
            permsRequestCode = permsRequestCode,
            permissions = permissions,
            grantResults = grantResults
        )
    }

    companion object {
        const val PHOTO_QUALITY = 100
        const val REQUEST_CODE = 300
    }
}