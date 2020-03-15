package co.eliseev.fingate.task.service

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

interface FeeScheduler {
    fun checkFee()
}

@Component
@EnableScheduling
class FeeSchedulerImpl(private val feeService: FeeService) : FeeScheduler {

    @Scheduled(cron = "\${fingate.tasks.fees.schedule}")
    override fun checkFee()  {
        logger.info("Scheduled checking all accounts adn withdraw fee")
        feeService.checkAllAccountsAndWithdrawFee()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FeeSchedulerImpl::class.java)
    }

}
