package com.example.widgettutorial

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import com.example.widgettutorial.ExampleWidgetConfigFile.Companion.BUTTON_TEXT
import com.example.widgettutorial.ExampleWidgetConfigFile.Companion.SHARED_PREFS

class ExampleAppWidgetProvider: AppWidgetProvider(){
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        for (appWidgetId  in appWidgetIds!!){
            val intent = Intent(context,MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context,0,intent,0)
            val views = RemoteViews(context?.packageName,R.layout.example_widget)

            val prefs = context?.getSharedPreferences(SHARED_PREFS, AppCompatActivity.MODE_PRIVATE)
            val btnText = prefs?.getString(BUTTON_TEXT+appWidgetId,"press me")

            views.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent)
            views.setCharSequence(R.id.example_widget_button,"setText",btnText)

            appWidgetManager?.updateAppWidget(appWidgetId,views)
        }
    }
}