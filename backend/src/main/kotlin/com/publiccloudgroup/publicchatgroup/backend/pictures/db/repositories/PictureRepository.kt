package com.publiccloudgroup.publicchatgroup.backend.pictures.db.repositories

import com.publiccloudgroup.publicchatgroup.backend.pictures.db.model.Picture
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import java.util.UUID

interface PictureRepository: ReactiveCrudRepository<Picture, UUID> {

    fun findAllByMessageId(messageId: UUID): Flux<Picture>

}
