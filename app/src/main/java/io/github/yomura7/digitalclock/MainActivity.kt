package io.github.yomura7.digitalclock

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var secondTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val textView = findViewById<TextView>(R.id.current_time)
        var handler = Handler()
        secondTimer = Timer().apply {
            val timerTask = object: TimerTask() {
                override fun run(){
                    handler.post {
                        textView.apply {
                            text = showCurrentTime()
                        }
                    }
                }
            }
            schedule(timerTask, 1000, 1000)
        }

        // Spinner1
        val textSizeSpinner = findViewById<Spinner>(R.id.text_size_spinner)
        textSizeSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.font_size_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textSizeSpinner.adapter = adapter
        }

        // TODO: Spinner2
        val textFontSpinner = findViewById<Spinner>(R.id.text_font_spinner)
        textFontSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.text_font_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textFontSpinner.adapter = adapter
        }

        // Spinner3
        val textColorSpinner = findViewById<Spinner>(R.id.text_color_spinner)
        textColorSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.text_color_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            textColorSpinner.adapter = adapter
        }

        // Spinner4
        val backgroundColorSpinner = findViewById<Spinner>(R.id.background_color_spinner)
        backgroundColorSpinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.background_color_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            backgroundColorSpinner.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val textView = findViewById<TextView>(R.id.current_time)
        val item = parent.getItemAtPosition(pos) as? String ?: return
        when (parent.id) {
            R.id.text_size_spinner -> textView.textSize = item.toFloat()
            R.id.text_color_spinner -> textView.setTextColor(Color.parseColor(item))
            R.id.text_font_spinner -> textView.typeface = Typeface.create(item, Typeface.NORMAL)
            R.id.background_color_spinner -> this.window.decorView.setBackgroundColor(Color.parseColor(item))
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
    }

    override fun onDestroy() {
        super.onDestroy()
        secondTimer.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showCurrentTime(): String = with(Date()) {
        val dateFormat = SimpleDateFormat("hh:mm:ss")
        dateFormat.format(this)
    }
}
