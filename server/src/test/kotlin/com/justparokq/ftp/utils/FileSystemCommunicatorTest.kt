package com.justparokq.ftp.utils

import junit.framework.TestCase.assertNull
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class FileSystemCommunicatorTest {

    private val pathProcessor = PathProcessor("./testStorageDirectory/")
    private val defaultCommunicator = FileSystemCommunicator(pathProcessor)

    @Test
    fun `test getDirectoryContent with invalid directory`() {
        val result = defaultCommunicator.getDirectoryContent("invalid/path")

        assertNull(result)
    }

    @Test
    fun `test getDirectoryContent with non-directory path`() {
        val expectedFiles = null

        val actualResult =
            defaultCommunicator.getDirectoryContent("./testStorageDirectory/photo.png")

        assertEquals(expectedFiles, actualResult)
    }

    @Test
    fun `test getDirectoryContent with valid directory path`() {
        val file1 = File("./testStorageDirectory/inner_directory")
        val file2 = File("./testStorageDirectory/photo.png")
        val file3 = File("./testStorageDirectory/photoJpg_2.jpg")
        val file4 = File("./testStorageDirectory/textFile.txt")
        val expectedFiles = listOf(file1, file2, file3, file4)

        val actualResult = defaultCommunicator.getDirectoryContent("/")

        assertEquals(expectedFiles, actualResult)
    }

    @Test
    fun `test getFile returns file`() {
        val expectedResult = File("./testStorageDirectory/photo.png")

        val actualResult = defaultCommunicator.getFile("photo.png")

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test getFile returns null when is not file`() {
        val expectedResult = null

        val actualResult = defaultCommunicator.getFile("inner_directory")

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `test getFile returns null when directory does not exist`() {
        val expectedResult = null

        val actualResult = defaultCommunicator.getFile("invalid")

        assertEquals(expectedResult, actualResult)
    }
}