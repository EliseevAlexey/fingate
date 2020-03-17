package co.eliseev.fingate.notification.service.event

import co.eliseev.fingate.notification.model.EventType
import co.eliseev.fingate.notification.model.event.WithdrawEvent
import co.eliseev.fingate.notification.service.NotificationProcessor
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

interface WithdrawEventListener {
    fun listen(event: WithdrawEvent)
}

@Component
class WithdrawEventListenerImpl(private val notificationProcessor: NotificationProcessor) : WithdrawEventListener {

    @Async
    @EventListener(WithdrawEvent::class)
    override fun listen(event: WithdrawEvent) {
        logger.info("Received withdraw event $event")
        notificationProcessor.process(event.user, EventType.WITHDRAW)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WithdrawEventListenerImpl::class.java)
    }

}
