package com.example.storyapp

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import java.util.regex.Pattern

class PasswordEditText : CustomEditText {
    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    private val pattern: Pattern = Pattern.compile(".{8,}")

    private fun isValid (s: CharSequence) : Boolean{
        return pattern.matcher(s).matches()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Input Your Password"
    }

    override fun init(){
        super.init()
        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.let { isValid(it) } == false && s.isNotEmpty()){
                    error = "Password needs minimum of 8 characters"
                }
            }
        })
    }

    override fun isError(): Boolean {
        return !isValid(text.toString())
    }
}