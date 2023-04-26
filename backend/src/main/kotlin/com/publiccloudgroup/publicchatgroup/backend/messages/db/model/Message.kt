package com.publiccloudgroup.publicchatgroup.backend.messages.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "MESSAGE")
data class Message(
    @Id
    @Column("ID")
    public var id: UUID?,
    @Column("CONTENT")
    public var content: String,
    @Column("CREATED_AT")
    public var createdAt: Int
)
