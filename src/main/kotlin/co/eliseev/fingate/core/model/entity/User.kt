package co.eliseev.fingate.core.model.entity

import co.eliseev.fingate.security.model.entity.Role
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = User.TABLE_NAME)
class User(

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
    var defaultAccount: BankAccount? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = JOIN_TABLE,
        joinColumns = [JoinColumn(name = JOIN_COLUMN)],
        inverseJoinColumns = [JoinColumn(name = INVERSE_JOIN_COLUMN)]
    )
    var roles: Set<Role> = emptySet()

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "users"
        private const val JOIN_TABLE = "user_roles"
        private const val JOIN_COLUMN = "user_id"
        private const val INVERSE_JOIN_COLUMN = "role_id"

    }

}
