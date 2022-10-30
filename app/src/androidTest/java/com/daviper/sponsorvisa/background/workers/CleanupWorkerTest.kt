package com.daviper.sponsorvisa.background.workers

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.work.testing.TestWorkerBuilder
import com.daviper.sponsorvisa.background.DATABASE_OUTPUT_FILE_NAME
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.concurrent.Executors.newSingleThreadExecutor

class CleanupWorkerTest {

    private lateinit var context: Context
    private lateinit var worker: CleanupWorker

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        worker = TestWorkerBuilder<CleanupWorker>(context, newSingleThreadExecutor()).build()
    }

    @Test
    fun test_csvIsRemoved() {
        //create csv file
        val fileUri = createCsvFile()

        // run clean up
        worker.doWork()

        // check the file does not exist
        assertFalse(uriFileExists(fileUri))
    }

    private fun createCsvFile(): Uri {
        val csvFile = File(context.filesDir, DATABASE_OUTPUT_FILE_NAME)
        if (!csvFile.exists()) {
            csvFile.createNewFile()
        }
        return csvFile.toUri()
    }

    private fun uriFileExists(fileUri: Uri): Boolean {
        return try {
            val file = fileUri.toFile()
            file.exists()
        } catch (ex: java.lang.IllegalArgumentException) {
            false
        }
    }
}