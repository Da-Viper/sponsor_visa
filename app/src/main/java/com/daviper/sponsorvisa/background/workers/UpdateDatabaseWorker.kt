package com.daviper.sponsorvisa.background.workers

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.daviper.sponsorvisa.background.KEY_DATABASE_OUTPUT
import com.daviper.sponsorvisa.domain.use_cases.CompanyUseCases
import com.daviper.sponsorvisa.domain.utils.parseCSV
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class UpdateDatabaseWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val useCases: CompanyUseCases
) :
    CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {


        // read from file
        val filename = inputData.getString(KEY_DATABASE_OUTPUT)
        val downloadedFile = Uri.parse(filename).toFile()

        // parse file to list of company
        val companies = parseCSV(applicationContext, downloadedFile)

        // truncate the database
        val deleteCount = useCases.deleteCompanies()
        Log.i(TAG, "Deleted $deleteCount companies")

        // store list in the database
        useCases.updateCompanies(companies)
        return Result.success()
    }


    companion object {
        const val TAG = "UpdateDatabaseWorker"
    }
}