package com.example.pzza.service

import com.amazonaws.SdkClientException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.AmazonS3Exception
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.File


@Service
class ObjectStorageServiceImpl @Autowired constructor(private var env:Environment) : ObjectStorageService{

    private var s3:AmazonS3? =null

    @PostConstruct
    override fun init() {
        try {
            val accessKey: String? = env.getProperty("s3.access-key")
            val secretKey: String? = env.getProperty("s3.secret-key")
            val regionName: String? = env.getProperty("s3.region-name")
            val endPoint: String? = env.getProperty("s3.end-point")

            s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(EndpointConfiguration(endPoint, regionName))
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
                .build()

        } catch (e: AmazonS3Exception) {
            println("AmazonS3Exception 발생:")
            e.printStackTrace()
        }
    }
    override fun downloadFile() {
        TODO("Not yet implemented")
    }

    override fun selectFile() {
        TODO("Not yet implemented")
    }

    override fun deleteFile() {
        TODO("Not yet implemented")
    }

    override fun deleteFolder() {
        TODO("Not yet implemented")
    }

    override fun createFile(bucketName:String, objectName:String, filePath:String) {
        try {
            s3?.putObject(bucketName, objectName, File(filePath))
            System.out.format("Object %s has been created.\n", objectName)
        } catch (e: AmazonS3Exception) {
            e.printStackTrace()
        } catch (e: SdkClientException) {
            e.printStackTrace()
        }
    }

    override fun createFolder(bucketName:String, folderName: String): Boolean {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = 0L
        objectMetadata.contentType = "application/x-directory"
        val putObjectRequest =
            PutObjectRequest(bucketName, folderName, ByteArrayInputStream(ByteArray(0)), objectMetadata)

        try {
            s3?.putObject(putObjectRequest)
            System.out.format("Folder %s has been created.\n", folderName)
            return true
        } catch (e: AmazonS3Exception) {
            e.printStackTrace()
            return false
        } catch (e: SdkClientException) {
            e.printStackTrace()
            return false
        }

    }
}