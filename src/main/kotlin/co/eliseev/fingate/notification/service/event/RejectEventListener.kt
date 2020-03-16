package co.eliseev.fingate.notification.service.event

import co.eliseev.fingate.notification.model.event.RejectEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

interface RejectEventListener {
    fun listen(event: RejectEvent)
}

@Component
class RejectEventListenerImpl : RejectEventListener {

    @Async
    @EventListener(RejectEvent::class)
    override fun listen(event: RejectEvent) {
        TODO("Not yet implemented")
    }

}
