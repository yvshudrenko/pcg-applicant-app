package com.publiccloudgroup.publicchatgroup.backend.pictures.dto

import java.util.*

data class Picture(
    public var id: UUID?,
    public var content: String,
    public var name: String
)
