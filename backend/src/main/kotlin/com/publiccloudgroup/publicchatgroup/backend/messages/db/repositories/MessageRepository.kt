package com.publiccloudgroup.publicchatgroup.backend.messages.db.repositories

import com.publiccloudgroup.publicchatgroup.backend.messages.db.model.Message
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface MessageRepository: ReactiveCrudRepository<Message, UUID>
