package com.example.storyapp

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LoginView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val editTextPaint = Paint()

}