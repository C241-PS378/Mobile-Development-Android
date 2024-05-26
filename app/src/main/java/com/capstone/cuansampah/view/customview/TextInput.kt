package com.capstone.cuansampah.view.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class TextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (inputType and InputType.TYPE_TEXT_VARIATION_PASSWORD != 0 && s.toString().length < 8) {
                    Log.d("form edit text", "password checking")
                    setError(
                        "The password must not be less than 8 characters.",
                        null
                    )
                } else if (inputType and InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS != 0 && !Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    setError(
                        "The email format is incorrect",
                        null
                    )
                }
                else {
                    error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {
                if (inputType and InputType.TYPE_TEXT_VARIATION_PASSWORD != 0 && s.toString().length < 8) {
                    setError(
                        "The password must not be less than 8 characters.",
                        null
                    )
                } else if (inputType and InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS != 0 && !Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    setError(
                        "The email format is incorrect",
                        null
                    )
                }
                else {
                    error = null
                }
            }
        })
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}