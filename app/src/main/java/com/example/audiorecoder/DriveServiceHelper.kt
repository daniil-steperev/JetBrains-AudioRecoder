package com.example.audiorecoder

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/** A class that helps to upload files to the Google Drive. */
class DriveServiceHelper(private var mDriveService: Drive) {
    private val mExecutor = Executors.newSingleThreadExecutor()

    /** A method that creates a file. */
    fun createFile(filePath : String) : Task<String> {
        return Tasks.call(mExecutor, Callable {
            val fileMetaData = File()
            fileMetaData.name = "Recodered Audio"
            val file = java.io.File(filePath)
            val mediaContent = FileContent("audio/mpeg", file)

            lateinit var myFile : File
            try {
                myFile = mDriveService.files().create(fileMetaData, mediaContent).execute()
            } catch (e : Exception) {
                e.printStackTrace()
            }

            myFile.id
        })
    }
}

