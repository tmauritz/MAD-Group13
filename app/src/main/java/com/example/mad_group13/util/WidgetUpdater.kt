package com.example.mad_group13.util

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.example.mad_group13.presentation.PetStatsWidgetProvider

object WidgetUpdater {
    fun updatePetStatsWidget(context: Context) {
        val intent = Intent(context, PetStatsWidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            val ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, PetStatsWidgetProvider::class.java))

            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        }
        context.sendBroadcast(intent)
    }
}
