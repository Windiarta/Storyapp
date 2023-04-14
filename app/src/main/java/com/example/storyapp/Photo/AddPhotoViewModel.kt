package com.example.storyapp.Photo

import androidx.lifecycle.ViewModel
import java.io.File

class AddPhotoViewModel: ViewModel() {
    private var file: File? = null

    fun getFile(): File? {
        return file
    }

    fun setFile(file: File?) {
        this.file = file
    }
}