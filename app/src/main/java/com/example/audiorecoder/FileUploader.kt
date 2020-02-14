@file:Suppress("DEPRECATION")

package com.example.audiorecoder

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import java.util.*

/** A class that uploads file to the Google Drive. */
class FileUploader(private val context: Context) {

    private lateinit var driveServiceHelper: DriveServiceHelper

    /** A method that uploads a file. */
    fun uploadFile() {
        val progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading to Google Drive")
        progressDialog.setMessage("Please wait...")
        progressDialog.show()

        val filePath = "/storage/emulated/0/recording.mp3"

        driveServiceHelper.createFile(filePath).addOnSuccessListener {
            progressDialog.dismiss()

            Toast.makeText(context, "Uploaded successfully", Toast.LENGTH_LONG).show()
        }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context, "Check your google drive api key", Toast.LENGTH_LONG).show()
            }
    }

    /** A method that handles to sign to Google Drive. */
    fun handleSignInIntent(data: Intent?) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnSuccessListener { googleSignInAccount ->
                val credential = GoogleAccountCredential.usingOAuth2(context, Collections.singleton(DriveScopes.DRIVE_FILE))

                credential.selectedAccount = googleSignInAccount.account
                val googleDriveService = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory(),
                    credential)
                    .setApplicationName("Audio Recoder")
                    .build()

                driveServiceHelper = DriveServiceHelper(googleDriveService)
            }
    }
}