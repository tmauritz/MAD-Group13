package com.example.mad_group13.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.mad_group13.R

class PetStatsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (widgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_pet_stats)

            // TODO: hier holst du aus SharedPreferences oder einem Cache die Pet-Werte
            val health = getHealthFromPrefs(context)
            val hunger = getHungerFromPrefs(context)
            val happiness = getHappinessFromPrefs(context)
            val activity = getActivityFromPrefs(context)

            Log.d("Widget", "Health loaded: $health")

            views.setTextViewText(R.id.health_text, "Health: ${health}%")
            views.setTextViewText(R.id.hunger_text, "Hunger: ${hunger}%")
            views.setTextViewText(R.id.happiness_text, "Happiness: ${happiness}%")
            views.setTextViewText(R.id.activity_text, "Activity: ${activity}%")

            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }

    private fun getHealthFromPrefs(context: Context): Int {
        return context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("health", 0)
    }

    private fun getHungerFromPrefs(context: Context): Int {
        return context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("hunger", 0)
    }

    private fun getHappinessFromPrefs(context: Context): Int {
        return context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("happiness", 0)
    }

    private fun getActivityFromPrefs(context: Context): Int {
        return context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("activity", 0)
    }
}
