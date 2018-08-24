package com.clw.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.PorterDuff.Mode
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class XCRoundRectImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    private val paint: Paint = Paint()
    /**
     * 绘制圆角矩形图片
     */
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        val drawable = drawable
        if (null != drawable) {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val b = getRoundBitmap(bitmap, 20)
            val rectSrc = Rect(0, 0, b.width, b.height)
            val rectDest = Rect(0, 0, width, height)
            paint.reset()
            canvas.drawBitmap(b, rectSrc, rectDest, paint)

        } else {
            super.onDraw(canvas)
        }
    }

    /**
     * 获取圆角矩形图片方法
     * @param bitmap
     * @param roundPx,一般设置成14
     * @return Bitmap
     */
    private fun getRoundBitmap(bitmap: Bitmap, roundPx: Int): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width,
                bitmap.height, Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe

        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        //val x = bitmap.width
        canvas.drawRoundRect(rectF, roundPx.toFloat(), roundPx.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output


    }
}
