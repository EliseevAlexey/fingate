package co.eliseev.fingate.audit.service

import co.eliseev.fingate.audit.entity.UserAction
import co.eliseev.fingate.audit.repository.UserActionRepository
import org.springframework.stereotype.Service

interface UserActionService {
    fun save(userAction: UserAction): UserAction
}

@Service
class UserActionServiceImpl(private val userActionRepository: UserActionRepository) : UserActionService {
    override fun save(userAction: UserAction): UserAction = userActionRepository.save(userAction)
}
