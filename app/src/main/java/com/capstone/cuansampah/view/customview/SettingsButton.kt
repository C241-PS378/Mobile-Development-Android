package com.capstone.cuansampah.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.cuansampah.R

class SettingsButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var background: Drawable

    init {
        txtColor = ContextCompat.getColor(context, R.color.black)
        background = ContextCompat.getDrawable(context, R.drawable.bg_button_setting) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setTextColor(txtColor)
    }
}