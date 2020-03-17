package co.eliseev.fingate.security.util

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ADMIN')")
annotation class HasAdminRights
