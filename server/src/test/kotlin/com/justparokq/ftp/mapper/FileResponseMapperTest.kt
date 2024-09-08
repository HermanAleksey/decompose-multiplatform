package com.justparokq.ftp.mapper

import com.justparokq.ftp.utils.PathProcessor
import com.justparokq.homeftp.models.ftp.FileResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File


internal class FileResponseMapperTest {

    private val pathProcessor = PathProcessor("./testStorageDirectory/")
    private val mapper = FileResponseMapper(pathProcessor)

    @Test
    fun `test map with empty list`() {
        val files = emptyList<File>()
        val result = mapper.map(files)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `test map with files`() {
        val files = listOf(
            File("./testStorageDirectory/inner_directory"),
            File("./testStorageDirectory/photo.png"),
            File("./testStorageDirectory/photoJpg_2.jpg"),
            File("./testStorageDirectory/textFile.txt"),
            File("./testStorageDirectory/inner_directory/innerPhoto.jpg"),
        )
        val expectedResult = listOf(
            FileResponse(
                uri = "inner_directory",
                name = "inner_directory",
                isDirectory = true
            ),
            FileResponse(
                uri = "photo.png",
                name = "photo.png",
                isDirectory = false
            ),
            FileResponse(
                uri = "photoJpg_2.jpg",
                name = "photoJpg_2.jpg",
                isDirectory = false
            ),
            FileResponse(
                uri = "textFile.txt",
                name = "textFile.txt",
                isDirectory = false
            ),
            FileResponse(
                uri = "inner_directory/innerPhoto.jpg",
                name = "innerPhoto.jpg",
                isDirectory = false
            ),
        )

        val result = mapper.map(files)

        assertEquals(expectedResult, result)
    }
}