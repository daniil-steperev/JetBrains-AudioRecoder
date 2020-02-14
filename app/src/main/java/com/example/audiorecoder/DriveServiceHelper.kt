package com.example.audiorecoder

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.api.client.http.FileContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class DriveServiceHelper {
    private val mExecutor = Executors.newSingleThreadExecutor()
    private var mDriveService : Drive

    constructor(mDriveService : Drive) {
        this.mDriveService = mDriveService
    }

    fun initDriveServiceHelper(mDriveService: Drive) {
        this.mDriveService
    }

    fun createFile(filePath : String) : Task<String> {
        return Tasks.call(mExecutor, Callable {
            val fileMetaData = File()
            fileMetaData.name = "Recodered Audio"
            var file = java.io.File(filePath)
            var mediaContent = FileContent("audio/mpeg", file)

            lateinit var myFile : File
            try {
                myFile = mDriveService.files().create(fileMetaData, mediaContent).execute()
            } catch (e : Exception) {
                e.printStackTrace()
            }

            if (myFile == null) {
                throw IOException("Null result when requesting file creation")
            }

            myFile.id
        })
    }
}

