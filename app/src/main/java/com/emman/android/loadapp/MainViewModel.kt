package com.emman.android.loadapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _downloadID = MutableLiveData<Long>()
    val downloadID: LiveData<Long>
        get() = _downloadID

    fun setDownloadID(id: Long) {
        _downloadID.value = id
    }

    private val _downloadStatus = MutableLiveData<String?>()
    val downloadStatus: LiveData<String?>
        get() = _downloadStatus

    fun setDownloadStatus(status: String?) {
        _downloadStatus.value = status
    }

    private val _fileName = MutableLiveData<String?>()
    val fileName: LiveData<String?>
        get() = _fileName

    fun setFileName(name: String?) {
        _fileName.value = name
    }
}
