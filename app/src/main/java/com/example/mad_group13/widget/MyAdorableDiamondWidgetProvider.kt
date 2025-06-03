package com.example.mad_group13.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class MyAdorableDiamondWidgetProvider: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MyAdorableDiamondWidget()
}