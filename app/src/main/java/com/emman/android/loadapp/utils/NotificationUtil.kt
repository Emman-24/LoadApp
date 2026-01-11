package com.emman.android.loadapp.utils

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.emman.android.loadapp.MainActivity
import com.emman.android.loadapp.R

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
    fileName: String,
    status: String
) {

    val contentPendingIntent = NavDeepLinkBuilder(applicationContext)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.detailFragment)
        .setArguments(Bundle().apply {
            putString("fileName", fileName)
            putString("status", status)
        })
        .setComponentName(MainActivity::class.java)
        .createPendingIntent()

    val action = NotificationCompat.Action.Builder(
        0,
        applicationContext.getString(R.string.notification_check),
        contentPendingIntent
    ).build()

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.cloud_download_24dp)
        .setContentTitle(applicationContext.getString(R.string.app_name))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .addAction(action)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Link that manage the pending intent in nav_graph:
 * https://susuthapa19961227.medium.com/handle-pending-intent-from-notification-android-navigation-component-908a1e32d7df
 */