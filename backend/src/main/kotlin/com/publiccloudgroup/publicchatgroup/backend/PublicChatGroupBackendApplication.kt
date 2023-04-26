package com.publiccloudgroup.publicchatgroup.backend

import com.publiccloudgroup.publicchatgroup.backend.messages.MessageConfiguration
import com.publiccloudgroup.publicchatgroup.backend.messages.db.model.Message
import com.publiccloudgroup.publicchatgroup.backend.pictures.PictureConfiguration
import com.publiccloudgroup.publicchatgroup.backend.pictures.db.model.Picture
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ByteArrayResource
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaField

@JvmRecord
data class Triple<TFirst, TSecond, TThird>(
    public val first: TFirst,
    public val second: TSecond,
    public val third: TThird
)

@SpringBootApplication(scanBasePackageClasses = [MessageConfiguration::class, PictureConfiguration::class])
class PublicChatGroupBackendApplication {
    @Bean
    fun initializer(connectionFactory: ConnectionFactory) = ConnectionFactoryInitializer().apply {
        setConnectionFactory(connectionFactory)
        setDatabasePopulator(
            CompositeDatabasePopulator()
                .apply {
                    addPopulators(ResourceDatabasePopulator(ByteArrayResource(createSchemaSqlFrom(Message::class, Picture::class).also(::println).toByteArray())))
                })
    }

    private fun createSchemaSqlFrom(vararg classes: KClass<*>) = classes
        .associateWith { entityClass ->
            entityClass.declaredMemberProperties
                .asSequence()
                .filter { property -> property !is KFunction<*> }
                .map {
                    Triple(
                        it.javaField!!.getAnnotation(Column::class.java)?.value ?: "\"${it.name}\"",
                        mapToDatabaseType(it.javaField!!.type.kotlin),
                        (if (it.returnType.isMarkedNullable) "" else "NOT NULL ") + if (it.javaField!!.isAnnotationPresent(Id::class.java))
                            (when (it.javaField!!.type) {
                                Int::class.java -> "AUTO INCREMENT "
                                UUID::class.java -> "default random_uuid() "
                                else -> ""
                            }) + "PRIMARY KEY"
                        else
                            ""
                    )
                }
                .joinToString(",\n") { "${it.first} ${it.second} ${it.third}".trim() }
        }
        .mapKeys { (key, _) -> key.findAnnotation<Table>()?.run {if (name.isNotEmpty()) name else if (value.isNotEmpty()) value else null} ?: "\"${key.simpleName}\"" }
        .map { (key, value) ->
            "CREATE TABLE IF NOT EXISTS ${key} (\n$value\n);"
        }
        .joinToString("\n")

    private fun mapToDatabaseType(type: KClass<out Any>) = when (type.qualifiedName) {
        "kotlin.Int" -> "INT"
        "kotlin.String" -> "VARCHAR"
        "kotlin.Boolean" -> "BOOLEAN"
        "java.util.UUID" -> "UUID"
        "io.r2dbc.spi.Blob" -> "BLOB"
        else -> throw IllegalArgumentException("Unmapped type ${type.qualifiedName}")
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .csrf {
            it.disable()
        }
        .build()
}

fun main(args: Array<String>) {
    runApplication<PublicChatGroupBackendApplication>(*args)
}
