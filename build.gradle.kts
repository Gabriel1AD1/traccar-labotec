plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.labotec"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
	implementation("commons-codec:commons-codec:1.15")
	// Dependencia principal de Logback
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache
	implementation("org.springframework.boot:spring-boot-starter-cache:3.3.4")

	// Dependencia para SLF4J
	implementation("org.slf4j:slf4j-api:2.0.9")
	implementation("org.springframework.boot:spring-boot-starter-logging")
	implementation("org.springframework.boot:spring-boot-starter-websocket")    // WebSocket para comunicación bidireccional
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.liquibase:liquibase-core:4.23.2")
	implementation("org.locationtech.spatial4j:spatial4j:0.8")
	implementation("org.locationtech.jts:jts-core:1.19.0")
	implementation("com.google.maps:google-maps-services:0.18.1")
	implementation("net.sf.geographiclib:GeographicLib-Java:1.50")
	implementation("com.sun.mail:javax.mail:1.6.2")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-rest
	// implementation("org.springframework.boot:spring-boot-starter-data-rest:3.3.3")
	compileOnly("org.projectlombok:lombok")
	compileOnly("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<Test> {
	useJUnitPlatform()
}
