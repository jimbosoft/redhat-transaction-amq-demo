import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.8.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "au.gov.bom.integration"
version = System.getenv("APP_VERSION") ?: "1.0.0"

dependencies {

	// fusePlugin
	implementation(platform("org.jboss.redhat-fuse:fuse-springboot-bom:7.4.0.fuse-sb2-740019-redhat-00005"))

	implementation("org.apache.camel:camel-spring-boot-starter")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// tracing
	implementation("io.opentracing.contrib:opentracing-spring-jaeger-starter:2.0.3")
	implementation("org.apache.camel:camel-opentracing-starter")

	testImplementation("org.apache.camel:camel-test")
	testImplementation("org.junit.vintage:junit-vintage-engine") {
		because("CamelTestSupport only support Junit 4")
	}

	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude("org.springframework.boot", "spring-boot-starter-tomcat")
	}
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation(kotlin("stdlib-jdk8"))

	// javaPlugin
	testImplementation(platform("org.junit:junit-bom:5.5.2"))
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

	// springBootPlugin
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE"))
	// common spring services
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	// process property beans for IDE
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	// test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		// junit 4 is excluded - we use junit 5
		exclude(group = "junit", module = "junit")
	}

	// amq libs
	implementation("org.messaginghub:pooled-jms")
	implementation("org.apache.camel:camel-amqp-starter")
}
repositories {
//	mavenCentral()
	maven {
		url = uri(System.getProperty("artifactRepoUrl"))
		credentials {
			username = System.getProperty("artifactRepoUsername")
			password = System.getProperty("artifactRepoPassword")
		}
	}
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}