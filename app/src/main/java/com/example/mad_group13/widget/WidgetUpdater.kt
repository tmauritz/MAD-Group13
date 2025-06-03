package com.example.mad_group13.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager

object WidgetUpdater {
    suspend fun updateMyAdorableDiamondWidget(context: Context){
        val manager = GlanceAppWidgetManager(context)
        val widget = MyAdorableDiamondWidget()
        val glanceIds = manager.getGlanceIds(widget.javaClass)
//        val intent = Intent(context, MyAdorableDiamondWidget::class.java).apply {
//            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
//
//            val ids = AppWidgetManager.getInstance(context)
//                .getAppWidgetIds(ComponentName(context, MyAdorableDiamondWidget::class.java))
//
//            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
//        }
//        context.sendBroadcast(intent)
        glanceIds.forEach { glanceId ->
            widget.update(context, glanceId)
        }
    }
}
