package co.eliseev.fingate.notification.service.event

import co.eliseev.fingate.notification.model.EventType
import co.eliseev.fingate.notification.model.event.RejectEvent
import co.eliseev.fingate.notification.service.NotificationProcessor
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

interface RejectEventListener {
    fun listen(event: RejectEvent)
}

@Component
class RejectEventListenerImpl(private val notificationProcessor: NotificationProcessor) : RejectEventListener {

    @Async
    @EventListener(RejectEvent::class)
    override fun listen(event: RejectEvent) {
        logger.info("Received reject event $event")
        notificationProcessor.process(event.user, EventType.REJECT)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(RejectEventListenerImpl::class.java)
    }

}
