package co.eliseev.fingate.security.util

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
annotation class HasAdminOrUserRights
