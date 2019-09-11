package com.example.popularmovieskotlin.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.popularmovieskotlin.database.getDatabase
import com.example.popularmovieskotlin.repository.MoviesRepository
import timber.log.Timber

class RefreshMovieWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshMovieWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = MoviesRepository(database)

        return try {
            var builder = NotificationCompat.Builder(applicationContext, "channel_id")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line...")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "channel name"
                val descriptionText = "description"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("channel_id", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(100, builder.build())
            }

            repository.refreshMovies()

            Timber.d("worker")
            Result.success()
        } catch (throwable: Throwable) {
            Result.retry()
        }
    }

}