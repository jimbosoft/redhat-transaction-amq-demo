package au.gov.bom.integration.somethings.routes

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class JmsRoute() : RouteBuilder() {
    private val testTopic = "jms:topic:app.test.multicast.address"
    private val ds =
        "subscriptionName=adapter-transactional-amq&subscriptionShared=true&subscriptionDurable=true&errorHandlerLogStackTrace=false"
    private val jmsUri = "$testTopic?$ds&jmsMessageType=Text"
    private var seq = 0

    override fun configure() {
        onException(Exception::class.java).handled(false).logExhausted(false).logStackTrace(false)

        from(jmsUri)
            .transacted()
            .routeId(testTopic)
            .process {exchange ->
                val msg = exchange.`in`.body as String
                seq++
                log.info("Received: $seq: $msg")
                if (msg.first() == '1' && seq < 10)
                {
                    log.error("delivery failed, throwing an exception")
                    throw Exception("Delivery failed")
                }
            }
    }
}