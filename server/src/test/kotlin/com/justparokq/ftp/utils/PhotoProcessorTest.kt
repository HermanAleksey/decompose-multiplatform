package com.justparokq.ftp.utils

import org.junit.Test
import kotlin.test.assertEquals

internal class PhotoProcessorTest {

    private val pathProcessor = PathProcessor()
    private val photoProcessor = PhotoProcessor(pathProcessor)

    @Test
    fun `should create preview file in root`() {
        val file = "./testStorageDirectory/photo.png"
        val expectedPreviewFilePath = "./testStorageDirectory/previews/preview_photo.png"

        val actualResult = photoProcessor.createPreviewFile(file)

        assertEquals(expectedPreviewFilePath, actualResult.path)
    }

    @Test
    fun `should create preview file in nested directory`() {
        val file = "./testStorageDirectory/inner_directory/innerPhoto.jpg"
        val expectedPreviewFilePath =
            "./testStorageDirectory/previews/inner_directory/preview_innerPhoto.jpg"

        val actualResult = photoProcessor.createPreviewFile(file)

        assertEquals(expectedPreviewFilePath, actualResult.path)
    }
}