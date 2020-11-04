package au.gov.bom.integration.somethings

import org.apache.camel.component.jms.JmsComponent
import org.apache.qpid.jms.JmsConnectionFactory
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.connection.JmsTransactionManager
import javax.jms.ConnectionFactory

@Configuration
class Beans() {

    @Bean
    fun jmsPoolConnectionFactory() =
        JmsPoolConnectionFactory().apply {
            connectionFactory = JmsConnectionFactory("admin", "admin", "failover:(amqp://localhost:5672?jms.tracing=opentracing)")
            maxConnections = 3
        }

    @Bean
    fun jms(jmsConnectionFactory: ConnectionFactory, jmsTransactionManager: JmsTransactionManager) =
        JmsComponent.jmsComponentTransacted(jmsConnectionFactory, jmsTransactionManager).apply { setCacheLevelName("CACHE_CONSUMER") }

    @Bean
    fun jmsTransactionManager(jmsConnectionFactory: ConnectionFactory) =
        JmsTransactionManager(jmsConnectionFactory)
}
