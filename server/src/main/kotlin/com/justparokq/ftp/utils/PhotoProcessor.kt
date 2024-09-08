package com.justparokq.ftp.utils

import com.justparokq.ftp.utils.PathConstants.PHOTO_TYPE_JPG
import com.justparokq.ftp.utils.PathConstants.PREVIEW_QUALITY
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.ImageOutputStream

internal class PhotoProcessor(
    private val pathProcessor: PathProcessor,
) {

    fun getPreviewForPhoto(inputFile: File): File {
        val previewFilePath = getPreviewFileNameForFile(inputFile)
        if (File(previewFilePath).exists()) {
            return File(previewFilePath)
        } else {
            val previewFile = createPreviewFile(previewFilePath)
            lowerImageQuality(inputFile, previewFile, quality = PREVIEW_QUALITY)
            return previewFile
        }

    }

    fun createPreviewFile(path: String): File {
        val previewFile = File(path)
        previewFile.parentFile.mkdirs()
        return previewFile
    }

    fun getPreviewFileNameForFile(inputFile: File): String {
        return inputFile.path
            .replace(
                pathProcessor.getRootDirectoryPath(),
                pathProcessor.getPreviewRootDirectoryPath()
            )
            .replace(
                inputFile.name,
                "preview_${inputFile.name}"
            )
    }

    fun lowerImageQuality(inputFile: File, outputFile: File, quality: Float) {
        if (quality < 0.0 || quality > 1.0) error("Invalid quality")

        val originalImage: BufferedImage = ImageIO.read(inputFile)

        val writers = ImageIO.getImageWritersByFormatName(PHOTO_TYPE_JPG)
        val writer: ImageWriter = writers.next()

        val ios: ImageOutputStream = ImageIO.createImageOutputStream(outputFile)
        writer.output = ios

        val param: ImageWriteParam = writer.defaultWriteParam
        if (param.canWriteCompressed()) {
            param.compressionMode = ImageWriteParam.MODE_EXPLICIT
            param.compressionQuality = quality
        }

        writer.write(null, IIOImage(originalImage, null, null), param)

        ios.close()
        writer.dispose()
    }
}