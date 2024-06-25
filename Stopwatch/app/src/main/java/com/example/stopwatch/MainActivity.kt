package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

// OnClickListener 인터페이스 구현
// 굉장히 많이 쓰이는 인터페이스 중 하나이다.
// 즉) 버튼이든 뭐든 클릭이 되었을 때 사용하는 인터페이스라고 생각하면 된다.
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btn_start : Button
    private lateinit var btn_refresh : Button
    private lateinit var tv_minute : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_millisecond : TextView

    private var isRunning = false

    private var timer : Timer? = null
    private var time = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        btn_refresh = findViewById(R.id.btn_refresh)
        tv_minute = findViewById(R.id.tv_minite)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause)
        btn_start.setBackgroundColor(getColor(R.color.btn_pause))
        isRunning = true

        timer = timer(period = 10) {
            // 1000ms = 1s
            // 0.01초 마다 time 1+
            time++

            val milli_second = time%100
            val second = (time % 6000) / 100
            val minute = time / 6000

            // 스레드에서 실행이 될 수 있도록 런온 UI 스레드가 처리를 해준다.
            // 바로 백그라운드 스레드에서는 ui 자원을 접근할 수 없다
            // ui 자원을 접근하기 위해서는 이런 runOnUiThread 함수를 사용해주면 된다.
            runOnUiThread{
                if(isRunning){
                    tv_millisecond.text = if(milli_second < 10) ".0${milli_second}" else ".${milli_second}"
                    tv_second.text = if(second < 10) ":0${second}" else ":${second}"
                    tv_minute.text = "${minute}"
                }
            }
        }
    }

    private fun pause() {
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))
        isRunning = false
        timer?.cancel()
    }

    private fun refresh() {
        timer?.cancel()
        btn_start.text = getString(R.string.btn_start)
        btn_start.setBackgroundColor(getColor(R.color.btn_start))
        isRunning = false

        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_start -> {
                if(isRunning){
                    pause()
                } else {
                    start()
                }
            }

            R.id.btn_refresh -> {
                refresh()
            }
        }
    }
}