plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "ncc.up.pt"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {	
	mavenCentral()
}

dependencies {
	implementation("javax.persistence:javax.persistence-api:2.2")
	implementation("com.google.zxing:core:3.5.3")
	implementation("org.json:json:20240205")
	implementation("com.google.zxing:javase:3.5.3")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("mysql:mysql-connector-java:8.0.28")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
