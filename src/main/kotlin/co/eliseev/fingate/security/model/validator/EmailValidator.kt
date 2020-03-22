package co.eliseev.fingate.security.model.validator

import co.eliseev.fingate.security.model.validator.exception.EmailValidationException
import org.slf4j.LoggerFactory
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [EmailValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailConstraint(
    val message: String = "Invalid email",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class EmailValidator : ConstraintValidator<EmailConstraint, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        logger.info("Value $logger")
        return value != null && isValidEmailAddress(value)
    }

    private fun isValidEmailAddress(email: String): Boolean =
        if (emailPattern.matcher(email).matches()) true
        else throw EmailValidationException("sing_up.email.validation", email)

    companion object {
        private val logger = LoggerFactory.getLogger(EmailValidator::class.java)
        private val emailPattern = // TODO move to props
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$".toPattern()
    }

}
