package top.greendami.blingview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            tv.setBling(blingWidth = 80f,angle = 20f,speed = 6f)
        }
        button1.setOnClickListener {
            tv.setBling(isStop = true)
        }
    }

}
