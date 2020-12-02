package com.example.widgettutorial

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RemoteViews

class ExampleWidgetConfigFile : AppCompatActivity() {
   companion object{
       val SHARED_PREFS = "prefs"
       val BUTTON_TEXT = "keyButtonText"
   }
    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example_widget_config_file)

        val configIntent = intent
        val extras = configIntent.extras

        if (extras != null){
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        val resultValue = intent
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        setResult(RESULT_OK,resultValue)

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish()
        }

        editText = findViewById(R.id.edit_text_button)

    }

    fun confirmConfiguration(view: View){

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val buttonText = editText.text.toString()
        val views = RemoteViews(packageName,R.layout.example_widget)

        views.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent)
        views.setCharSequence(R.id.example_widget_button,"setText",buttonText)
        appWidgetManager?.updateAppWidget(appWidgetId,views)

        val prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(BUTTON_TEXT+appWidgetId,buttonText)
        editor.apply()

        val resultValue = intent
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        setResult(RESULT_OK,resultValue)
        finish()


    }
}