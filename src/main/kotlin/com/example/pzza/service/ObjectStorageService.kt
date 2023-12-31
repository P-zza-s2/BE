package com.example.pzza.service

import jakarta.annotation.PostConstruct

interface ObjectStorageService {
    fun init()
    fun downloadFile()
    fun selectFile()
    fun deleteFile()
    fun deleteFolder()
    fun createFile(bucketName:String, objectName:String, filePath:String)
    fun createFolder(bucketName:String, folderName: String):Boolean
}