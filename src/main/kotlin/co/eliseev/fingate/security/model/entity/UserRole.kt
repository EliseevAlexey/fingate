package co.eliseev.fingate.security.model.entity

import co.eliseev.fingate.security.service.exception.ParseRoleException

enum class UserRole {
    USER,
    ADMIN;

    companion object {
        fun parse(name: String) = values().firstOrNull { it.name == name } ?:
            throw ParseRoleException("roles.parse.exception", name)
    }

}
