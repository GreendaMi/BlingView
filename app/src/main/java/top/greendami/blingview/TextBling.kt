package top.greendami.blingview

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.widget.TextView
import org.jetbrains.anko.runOnUiThread

/**
 * Created by GreendaMi on 2018/1/16.
 */

//是否有动画
fun TextView.isBling(): Boolean {
    if (this.tag != null) {
        var mT = this.tag as Thread
        return mT.isAlive
    }
    return false
}


fun TextView.setBling(textColor: Int = Color.BLACK, blingColor: Int = Color.WHITE, blingWidth: Float = this.textSize, isStop: Boolean = false, speed: Float = 4f,angle :Float = 0f) {
    var position = 0f
    //如果已经有发光效果
    if (this.tag != null) {
        var mT = this.tag as Thread
        mT.interrupt()
        this.tag = null
        blingText(null)
    }
    if (isStop) {
        return
    }
    var mThread = Thread(Runnable {
        while (!Thread.currentThread().isInterrupted) {
            //下一帧
            position += speed
            blingText(LinearGradient(0f + position, 0f, blingWidth + position, angle, textColor, blingColor, Shader.TileMode.MIRROR))
            try {
                //延迟
                Thread.sleep(100)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    })
    this.tag = mThread
    mThread.start()
}

private fun TextView.blingText( lg: LinearGradient?) {
    try {
        val class1 = Class.forName("android.widget.TextView")
        val field = class1.getDeclaredField("mTextPaint")
        field.isAccessible = true
        //拿到画笔对象
        var paint = field.get(this) as TextPaint

        this.context.runOnUiThread {
            //设置LinearGradient
            paint.shader = lg
            //呼叫重绘
            this@blingText.postInvalidate()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}