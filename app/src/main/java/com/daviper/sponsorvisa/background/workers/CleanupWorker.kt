package com.daviper.sponsorvisa.background.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.daviper.sponsorvisa.background.DATABASE_OUTPUT_FILE_NAME
import java.io.File

private const val TAG = "CleanupWorker"

class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {


    override fun doWork(): Result {
        return try {
            val outputFile = File(applicationContext.filesDir, DATABASE_OUTPUT_FILE_NAME)
            if (!outputFile.exists()) {
                Result.failure()
            }
            outputFile.delete()
            Log.d(TAG, "deleted file: ${outputFile.absolutePath}")
            Result.success()
        }
        catch (ex: NullPointerException){
            Log.e(TAG, "Failed to create file in : ${applicationContext.filesDir}/${DATABASE_OUTPUT_FILE_NAME}")
            Result.failure()
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure()
        }
    }
}
