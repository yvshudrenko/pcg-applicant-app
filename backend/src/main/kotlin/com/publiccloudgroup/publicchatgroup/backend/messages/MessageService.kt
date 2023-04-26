package com.publiccloudgroup.publicchatgroup.backend.messages

import com.publiccloudgroup.publicchatgroup.backend.messages.db.repositories.MessageRepository
import com.publiccloudgroup.publicchatgroup.backend.messages.dto.Message
import com.publiccloudgroup.publicchatgroup.backend.pictures.db.PictureService
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
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
            .map { MessageDto(it.content, it.id, listOf()) }
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
                pictures
            )
        } else {
            message
        }
    }

    private inline fun <T, R> Flux<T>.map2(crossinline mapFunction: (T) -> R) = map {
        mapFunction(it)
    }

    private inline fun <T> Flux<T>.tap(crossinline tapFunction: (T) -> Unit) = map2 {
        tapFunction(it)
        it
    }

    suspend fun getAll(): Iterable<MessageDto> = coroutineScope {
        messageRepository.findAll()
            .doOnEach { if (it.hasValue()) println(it.get()!!) }
            .map2 { MessageDto(it.content, it.id, listOf()) }
            .map2 { async { loadPicturesOf(it) } }
            .buffer()
            .defaultIfEmpty(listOf())
            .awaitFirst()
            .awaitAll()
    }

    suspend fun addMessage(message: MessageDto): MessageDto {
        val dbMessage = messageRepository.save(DbMessage(null, message.content)).awaitLast()
        return loadPicturesOf(MessageDto(dbMessage.content, dbMessage.id, listOf()))
    }
}
