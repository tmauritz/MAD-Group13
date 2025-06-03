package com.example.mad_group13.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.example.mad_group13.MainActivity

class MyAdorableDiamondWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val health = context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("health", 0)
        val happiness = context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("happiness", 0)
        val hunger = context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("hunger", 0)
        val activity = context.getSharedPreferences("pet_prefs", Context.MODE_PRIVATE)
            .getInt("activity", 0)

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            GlanceTheme{
                // create your AppWidget here
                Scaffold(titleBar = {
                    Text("Pet Stats:", style = TextStyle(fontStyle = FontStyle.Italic, color = GlanceTheme.colors.onSurface, textAlign = TextAlign.Center), modifier = GlanceModifier.fillMaxWidth().padding(top = 5.dp))
                },
                    backgroundColor = GlanceTheme.colors.background,
                    modifier = GlanceModifier.fillMaxSize().clickable(onClick = actionStartActivity<MainActivity>()))
                {
                    Column(modifier = GlanceModifier.fillMaxSize(), horizontalAlignment = Alignment.Horizontal.CenterHorizontally) {
                        WidgetStatDisplay("Health", health.toFloat()/100)
                        WidgetStatDisplay("Hunger", hunger.toFloat()/100)
                        WidgetStatDisplay("Happiness", happiness.toFloat()/100)
                        WidgetStatDisplay("Activity", activity.toFloat()/100)
                    }
                }
            }
        }
    }
}

@Composable
fun WidgetStatDisplay(label: String, progress: Float){
    Box(modifier = GlanceModifier.fillMaxWidth().padding(5.dp)){
        LinearProgressIndicator(progress = progress, modifier = GlanceModifier.fillMaxSize().height(20.dp))
        Text(label, style = TextStyle(color = GlanceTheme.colors.onSurface, textAlign = TextAlign.Center), modifier = GlanceModifier.fillMaxWidth())
    }
}
