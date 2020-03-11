package co.eliseev.fingate.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = User.TABLE_NAME)
class User(

    @Column(name = "email", nullable = false)
    private val email: String,

    @Column(name = "password", nullable = false)
    private val password: String,

    @Column(name = "first_name")
    private val firstName: String? = null,

    @Column(name = "last_name")
    private val lastName: String? = null

) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "users"
    }

}
