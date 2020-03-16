package co.eliseev.fingate.notification.service.event

import co.eliseev.fingate.notification.model.event.FundEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

interface FundEventListener {
    fun listen(event: FundEvent)
}

@Component
class FundEventListenerImpl: FundEventListener {

    @Async
    @EventListener(FundEvent::class)
    override fun listen(event: FundEvent) {
        TODO("Not yet implemented")
    }

}
