package com.publiccloudgroup.publicchatgroup.backend.messages

import com.publiccloudgroup.publicchatgroup.backend.messages.dto.Message
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/messages")
class MessageController(
    private val messageService: MessageService
) {

    @GetMapping(path = ["{id}"])
    suspend fun getMessage(@PathVariable id: String) = messageService.getMessage(UUID.fromString(id))

    @GetMapping
    suspend fun getAllMessages() = messageService.getAll()

    @PostMapping
    suspend fun addMessage(message: Message) = messageService.addMessage(message)

}
