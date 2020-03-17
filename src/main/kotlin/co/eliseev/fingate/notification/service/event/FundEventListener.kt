package co.eliseev.fingate.notification.service.event

import co.eliseev.fingate.notification.model.EventType
import co.eliseev.fingate.notification.model.event.FundEvent
import co.eliseev.fingate.notification.service.NotificationProcessor
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

interface FundEventListener {
    fun listen(event: FundEvent)
}

@Component
class FundEventListenerImpl(private val notificationProcessor: NotificationProcessor) : FundEventListener {

    @Async
    @EventListener(FundEvent::class)
    override fun listen(event: FundEvent) {
        logger.info("Received fund event $event")
        notificationProcessor.process(event.user, EventType.FUND)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FundEventListenerImpl::class.java)
    }

}
