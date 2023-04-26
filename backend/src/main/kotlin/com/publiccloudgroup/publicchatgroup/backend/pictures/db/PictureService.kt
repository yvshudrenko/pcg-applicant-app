package com.publiccloudgroup.publicchatgroup.backend.pictures.db

import com.publiccloudgroup.publicchatgroup.backend.pictures.db.repositories.PictureRepository
import com.publiccloudgroup.publicchatgroup.backend.pictures.dto.Picture
import io.r2dbc.spi.Blob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.net.URLDecoder
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO
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

    fun Image.toBlob() = Blob.from(ByteBuffer.wrap(ImageIO.createImageOutputStream(this)
        .readUTF()
        .toByteArray(Charsets.UTF_8)).toMono())

    suspend fun getPicture(id: UUID): Picture = this.pictureRepository.findById(id)
        .flatMap { it.content.toDataUrl("image/png").map { url -> Picture(it.id, url, it.name) }.toMono() }
        .awaitFirst()

    suspend fun getPicturesForMessage(messageId: UUID): Iterable<Picture> = this.pictureRepository.findAllByMessageId(messageId)
        .flatMap { it.content.toDataUrl("image/png").map { url -> Picture(it.id, url, it.name) }.toMono() }
        .buffer()
        .defaultIfEmpty(listOf())
        .awaitFirst()

    suspend fun saveImage(picture: Picture, messageId: UUID): Picture {
        val image = picture.content.dataUrlToImage()
            .grayscale()
            .toBlob()
        return pictureRepository.save(DbPicture(null, image, picture.name, messageId))
            .flatMap { it.content.toDataUrl("image/png").map { url -> Picture(it.id, url, it.name) }.toMono() }
            .awaitFirst()
    }

}

private fun BufferedImage.grayscale(): Image {
    for (x in 0 until width)
        for (y in 0 until height) {
            val rgb = getRGB(x, y)
            val a = rgb shr 24 and 0xFF
            val r = rgb shr 16 and 0xFF
            val g = rgb shr 8 and 0xFF
            val b = rgb and 0xFF
            val brightness = (255 - (r.toDouble() * redWeight + g.toDouble() * greenWeight + b.toDouble() * blueWeight)).roundToInt()
            setRGB(x, y, a shl 24 + (brightness shl 16) + (brightness shl 8) + brightness)
        }
    return this
}
