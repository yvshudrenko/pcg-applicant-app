package com.publiccloudgroup.publicchatgroup.backend.pictures

import com.publiccloudgroup.publicchatgroup.backend.pictures.db.repositories.PictureRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories(basePackageClasses = [PictureRepository::class])
class PictureConfiguration
