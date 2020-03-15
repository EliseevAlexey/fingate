package co.eliseev.fingate.core.model.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = User.TABLE_NAME)
data class User(

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "first_name")
    val firstName: String? = null,

    @Column(name = "last_name")
    val lastName: String? = null,

    @OneToMany
    val bankAccounts: Set<BankAccount> = emptySet(),

    @Column(name = "birthday")
    val birthday: LocalDate? = null,

    @OneToOne
    var defaultAccount: BankAccount? = null

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "users"
    }

}
