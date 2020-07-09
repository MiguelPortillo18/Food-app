package com.wrmh.allmyfood.models

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

@Suppress("DEPRECATION")
class SelectedFilePath {
    companion object {
        fun getPath(context: Context, uri: Uri): String? {

            //check here to KITKAT or new version
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":")
                    val type = split[0]

                    if ("primary".contentEquals(type)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://<span id=\" IL_AD1 \" class=\" IL_AD \">downloads</span>/public_downloads"),
                        id.toLong()
                    )

                    return getDataColumn(context, contentUri, null, null)
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":")
                    val type = split[0]

                    var contentUri: Uri? = null

                    when {
                        "image".equals(type, true) -> {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        "video" == type -> {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        "audio" == type -> {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri!!, selection, selectionArgs)
                }
            }

            // MediaStore (and general)
            else if ("content".equals(uri.scheme, true)) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.lastPathSegment!!

                return getDataColumn(context, uri, null, null)
            }
            // File
            else if ("file".equals(uri.scheme, true)) {
                return uri.path!!
            }

            return null
        }

        private fun getDataColumn(
            context: Context, uri: Uri, selection: String?,
            selectionArgs: Array<String>?
        ): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(
                    uri, projection, selection, selectionArgs,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean
        {
            return "com.android.externalstorage.documents" == uri.authority
        }


        private fun isDownloadsDocument(uri: Uri): Boolean
        {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean
        {
            return "com.android.providers.media.documents" == uri.authority
        }


        private fun isGooglePhotosUri(uri: Uri): Boolean
        {
            return "com.google.android.apps.photos.content" == uri.authority
        }
    }
}
