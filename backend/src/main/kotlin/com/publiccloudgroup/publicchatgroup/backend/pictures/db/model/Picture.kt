package com.publiccloudgroup.publicchatgroup.backend.pictures.db.model

import io.r2dbc.spi.Blob
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table(name = "PICTURE")
data class Picture(
    @Id
    @Column("ID")
    public var id: UUID?,
    @Column("CONTENT")
    public var content: Blob,
    @Column("NAME")
    public var name: String,
    @Column("MESSAGE_ID")
    public var messageId: UUID
)
