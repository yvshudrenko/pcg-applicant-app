package com.publiccloudgroup.publicchatgroup.backend.messages

import com.publiccloudgroup.publicchatgroup.backend.messages.db.repositories.MessageRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories(basePackageClasses = [MessageRepository::class])
class MessageConfiguration {
}
