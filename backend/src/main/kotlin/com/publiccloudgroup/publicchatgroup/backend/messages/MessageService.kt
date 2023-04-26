package com.publiccloudgroup.publicchatgroup.backend.messages

import com.publiccloudgroup.publicchatgroup.backend.messages.db.repositories.MessageRepository
import com.publiccloudgroup.publicchatgroup.backend.messages.dto.Message
import com.publiccloudgroup.publicchatgroup.backend.pictures.db.PictureService
import com.publiccloudgroup.publicchatgroup.backend.pictures.dto.Picture
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*
import com.publiccloudgroup.publicchatgroup.backend.messages.db.model.Message as DbMessage
import com.publiccloudgroup.publicchatgroup.backend.messages.dto.Message as MessageDto

@Component
class MessageService(
    private val messageRepository: MessageRepository,
    private val picturesService: PictureService
) {

    suspend fun getMessage(id: UUID): MessageDto {
        return messageRepository.findById(id)
            .map { MessageDto(it.content, it.id, listOf(), it.createdAt) }
            .awaitFirstOrElse { throw NoSuchElementException("No message $id") }
            .let { loadPicturesOf(it) }
    }

    private suspend fun loadPicturesOf(message: MessageDto): Message {
        val id = message.id
        return if (id != null) {
            val pictures = picturesService.getPicturesForMessage(id)
            MessageDto(
                message.content,
                message.id,
                pictures.toList(),
                message.createdAt
            )
        } else {
            message
        }
    }

    suspend fun getAll(): Iterable<MessageDto> = coroutineScope {
        messageRepository.findAll()
            .map { MessageDto(it.content, it.id, listOf(), it.createdAt) }
            .flatMap { mono { loadPicturesOf(it) } }
            .buffer()
            .defaultIfEmpty(listOf())
            .awaitFirst()
    }

    suspend fun addMessage(message: MessageDto): MessageDto = coroutineScope {

        val nowInUnixTimeSeconds = (System.currentTimeMillis() / 1000).toInt()
        val dbMessage = messageRepository.save(DbMessage(null, message.content, nowInUnixTimeSeconds)).awaitFirst()

        /*
        val pictures = Flux.fromIterable(message.pictures ?: listOf())
            .flatMap { mono { picturesService.saveImage(it, dbMessage.id!!) } }
            .buffer()
            .awaitLast()
        */



        val xxx = Flux.fromIterable(message.pictures ?: listOf())
            .flatMap { mono { picturesService.saveImage(it, dbMessage.id!!)} }
            .buffer()
            .awaitLast()

        val pictures = listOf<Picture>()

        loadPicturesOf(MessageDto(dbMessage.content, dbMessage.id, pictures, dbMessage.createdAt))

    }
}
