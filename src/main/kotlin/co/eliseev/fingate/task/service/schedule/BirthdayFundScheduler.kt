package co.eliseev.fingate.task.service.schedule

import co.eliseev.fingate.task.service.BirthdayFundService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

interface BirthdayFundScheduler {
    fun checkBirthday()
}

@Component
class BirthdayFundSchedulerImpl(private val birthdayFundService: BirthdayFundService) : BirthdayFundScheduler {

    @Scheduled(cron = "\${fingate.tasks.gifts.birthday.schedule}")
    override fun checkBirthday() {
        logger.info("Scheduled checking client birthday and fund")
        birthdayFundService.checkBirthdayAndFund()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(BirthdayFundSchedulerImpl::class.java)
    }

}
