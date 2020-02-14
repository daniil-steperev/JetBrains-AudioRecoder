package com.example.audiorecoder

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.lang.Exception

class FileUploader {

    private val context : Context

    constructor(context : Context) {
        this.context = context
    }

    fun uploadFile() {
        var progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Uploading to Google Drive")
        progressDialog.setMessage("Please wait...")
        progressDialog.show()

        val filePath = "/storage/emulated/0/recording.mp3"

        driveServiceHelper.createFile(filePath).addOnSuccessListener(object :
            OnSuccessListener<String> {
            override fun onSuccess(s : String) {
                progressDialog.dismiss()

                Toast.makeText(applicationContext, "Uploaded successfully", Toast.LENGTH_LONG).show()
            }
        })
            .addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e : Exception) {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Check your google drive api key", Toast.LENGTH_LONG).show()
                }
            })
    }
}