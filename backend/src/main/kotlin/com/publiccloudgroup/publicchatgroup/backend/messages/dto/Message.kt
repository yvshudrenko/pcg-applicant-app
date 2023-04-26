package com.publiccloudgroup.publicchatgroup.backend.messages.dto

import com.publiccloudgroup.publicchatgroup.backend.pictures.dto.Picture
import java.util.UUID

data class Message(
    var content: String,
    var id: UUID?,
    var pictures: Iterable<Picture>?
)
