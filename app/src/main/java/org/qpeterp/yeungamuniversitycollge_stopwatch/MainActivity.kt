package org.qpeterp.yeungamuniversitycollge_stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.qpeterp.yeungamuniversitycollge_stopwatch.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    private var time: Int = 0
    private var timerTask: Timer? = null
    private var isRunning: Boolean = false
    private var lap: Int = 1

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)//(R.layout.activity_main)

        binding.startBtn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        binding.lapTimeBtn.setOnClickListener {
            recordLapTime()
        }

        binding.refreshBtn.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        binding.startBtn.setImageResource(R.drawable.pause)
        timerTask = timer(period = 10) {
            time++
            val sec = time/100
            val milSec = time%100

            runOnUiThread {
                binding.secTextView.text = "$sec"
                binding.milSecTextView.text = "$milSec"
            }
        }
    }

    private fun pause() {
        binding.startBtn.setImageResource(R.drawable.play)
        timerTask?.cancel()
    }

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime/100}.${lapTime%100}"

        binding.lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
        this.pause()
        time = 0
        isRunning = false
        binding.secTextView.text = "0"
        binding.milSecTextView.text = "00"

        binding.lapLayout.removeAllViews()
        lap = 1

    }
}