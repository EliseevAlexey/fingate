package co.eliseev.fingate.core.repository

import co.eliseev.fingate.core.model.entity.Gift
import co.eliseev.fingate.core.model.entity.GiftType
import org.springframework.data.jpa.repository.JpaRepository

interface GiftRepository: JpaRepository<Gift, Long> {
    fun getByGiftType(giftType: GiftType): Gift?
}
