package au.gov.bom.integration.somethings

import org.apache.camel.opentracing.starter.CamelOpenTracing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Entry-point to the application
 */
@CamelOpenTracing
@SpringBootApplication
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
