package com.example.levelup.data.repository

import com.example.levelup.data.model.QrResult

class QrRepository {
    fun processQrContent(content: String): QrResult {
        return QrResult(content)
    }
}
