package co.eliseev.fingate.security.service

import co.eliseev.fingate.security.model.entity.Role
import co.eliseev.fingate.security.model.entity.UserRole
import co.eliseev.fingate.security.repository.RoleRepository
import co.eliseev.fingate.security.service.exception.RoleNotFoundException
import org.springframework.stereotype.Service

interface RoleService {
    fun getByName(name: UserRole): Role
}

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository
) : RoleService {

    override fun getByName(name: UserRole): Role =
        roleRepository.findByName(name)
            ?: throw RoleNotFoundException("roles.not_found", name)

}
