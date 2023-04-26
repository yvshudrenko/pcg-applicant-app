import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.0-RC1"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.20"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
}

group = "com.publiccloudgroup"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
	mavenCentral()
	maven {
		url = uri("https://repo.spring.io/milestone")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.0.6")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

	implementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.1.0")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")

	implementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "19"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
