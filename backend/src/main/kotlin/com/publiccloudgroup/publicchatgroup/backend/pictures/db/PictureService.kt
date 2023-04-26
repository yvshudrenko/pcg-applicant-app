package com.publiccloudgroup.publicchatgroup.backend.pictures.db

import com.publiccloudgroup.publicchatgroup.backend.pictures.db.repositories.PictureRepository
import com.publiccloudgroup.publicchatgroup.backend.pictures.dto.Picture
import io.r2dbc.spi.Blob
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.URLDecoder
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO
import javax.swing.GrayFilter
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.roundToInt
import com.publiccloudgroup.publicchatgroup.backend.pictures.db.model.Picture as DbPicture

const val redWeight = .2989
const val greenWeight = .5870
const val blueWeight = .1140

@OptIn(ExperimentalEncodingApi::class)
@Component
class PictureService(
    private val pictureRepository: PictureRepository
) {

    fun ByteArray.toBase64() = Base64.encode(this)

    fun String.toBinary() = Base64.decode(this)

    fun Blob.toDataUrl(mimeType: String) = stream()
        .toFlux()
        .buffer()
        .map {
            "data:$mimeType;base64," + it.fold(ByteArray(0)) { acc, byteBuffer -> acc + byteBuffer.array() }
                .toBase64()
        }


    fun String.dataUrlToImage() = if (!startsWith("data:"))
        throw IllegalArgumentException("data urls must start with \"data:\"")
    else {
        val (meta, content) = substring(5).split(",")
        val metadataFields = meta.split(";")
        val isBase64 = metadataFields.contains("base64")
        val mimeType = metadataFields.find { it != "base64" && !it.startsWith("charset=") } ?: throw IllegalArgumentException("data url for image must have MIME type declared.")
        val decodedContent = if (isBase64)
            content.toBinary()
        else
            URLDecoder.decode(content, Charset.defaultCharset()).encodeToByteArray()
        ImageIO.read(ByteArrayInputStream(decodedContent))
    }

    fun BufferedImage.toByteBuffer() = ByteArrayOutputStream().let {
        ImageIO.write(this, "png", it)
        ByteBuffer.wrap(it.toByteArray())!!
    }

    fun ByteBuffer.toBlob() = Blob.from(this.toMono())

    suspend fun getPicturesForMessage(messageId: UUID): Iterable<Picture> = this.pictureRepository.findAllByMessageId(messageId)
        .flatMap { it.content.toDataUrl("image/png").map { url -> Picture(it.id, url, it.name) }.toMono() }
        .buffer()
        .defaultIfEmpty(listOf())
        .awaitFirst()

    suspend fun saveImage(picture: Picture, messageId: UUID): Picture {
        val imageData = picture.content.dataUrlToImage()
            .grayscale()
            .toByteBuffer()

        return pictureRepository.save(DbPicture(null, imageData.toBlob(), picture.name, messageId))
            .flatMap { imageData.toBlob().toDataUrl("image/png").map { url -> Picture(it.id, url, it.name) }.toMono() }
            .awaitFirst()
    }

}

private fun BufferedImage.grayscale(): BufferedImage {
    val result = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
     for (x in 0 until width)
         for (y in 0 until height) {
             val rgb = getRGB(x, y)
             val color = Color(rgb)

             val r = color.red
             val g = color.green
             val b = color.blue
             val brightness = (r.toDouble() * redWeight + g.toDouble() * greenWeight + b.toDouble() * blueWeight).roundToInt()
             val grayColor = Color(brightness, brightness, brightness)
             result.setRGB(x, y, grayColor.rgb)
         }
     return result
 }

