package com.daviper.sponsorvisa.background.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.daviper.sponsorvisa.data.local.AppPreferencesRepository
import com.daviper.sponsorvisa.ui.dataStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class GetDataBaseWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val client: OkHttpClient
) : CoroutineWorker(ctx, params) {

    private val prefRepo: AppPreferencesRepository

    init {
        prefRepo = AppPreferencesRepository(ctx.dataStore)
    }

    override suspend fun doWork(): Result {

        // check if it is latest version
        if (isLatestVersion()) return Result.failure()

        Log.d(TAG, "This is not the latest version")

        return withContext(Dispatchers.IO) {
            // download latest version
            try {
                downloadLatestVersion()
                Result.success(workDataOf("outputfile" to fileOutputName))
            } catch (ex: IOException) {
                Log.w(TAG, ex.message ?: "")
                Result.failure()
            }
        }
    }

    private fun downloadLatestVersion() {
        val fileUrl = fetchDownloadUrl() ?: throw  IOException()
        Log.i(TAG, "the url is ${fileUrl}")
        downloadFile(fileOutputName, fileUrl)
    }

    private suspend fun isLatestVersion(): Boolean {
        val apiVersion = fetchApiLastUpdateDate() ?: return true
        val localVersion = prefRepo.getDatabaseVersion() ?: 0

        Log.d(TAG, "Api version: $apiVersion")
        Log.d(TAG, "Local version: $localVersion")
        Log.d(TAG, (localVersion >= apiVersion).toString())

        return localVersion >= apiVersion
    }

    private fun fetchDownloadUrl(): String? {
        // fallback to an older version
        // get the download link
        val doc = Jsoup.connect("${homepage}/${pageEndPoint}").get()

        return doc.select(downloadLinkCssQuery)
            .parallelStream()
            .map { it.attr("href") } // the download link
            .findFirst().orElse(null)

    }

    private fun getLastUpdate(): String? {
        // find the day the file was last updated
        val doc = Jsoup.connect("${homepage}/${pageEndPoint}").get()

        return doc.select(lastUpdateCssQuery)
            .parallelStream()
            .map { it.text() }
            .findFirst().orElse(null)
    }

    private fun fetchApiLastUpdateDate(): Long? {
        return getLastUpdate()?.let { stringToUnixEpoch(it) }
    }

    private fun stringToUnixEpoch(text: String): Long? {
        val date = SimpleDateFormat(datePattern, Locale.US).parse(text)
        if (date != null) {
            return TimeUnit.MILLISECONDS.toDays(date.time)
        }
        return null
    }

    private fun downloadFile(filename: String, url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            applicationContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(response.body?.bytes())
            }
        }
    }

    companion object {
        const val TAG = "GetDatabaseWorker"
        private const val homepage = "https://www.gov.uk"
        private const val pageEndPoint =
            "government/publications/register-of-licensed-sponsors-workers"
        private const val lastUpdateCssQuery =
            ".govuk-grid-row:nth-child(2) dl dd:last-child" //  dd.gem-c-metadata__definition > a "
        private const val downloadLinkCssQuery = "div.attachment-details .download > a"
        private const val fileOutputName = "licensed_sponsors.csv"
        private const val datePattern = "dd MMM yyyy"
    }
}