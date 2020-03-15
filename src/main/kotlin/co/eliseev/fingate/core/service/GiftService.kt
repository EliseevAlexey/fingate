package co.eliseev.fingate.core.service

import co.eliseev.fingate.core.model.entity.Gift
import co.eliseev.fingate.core.model.entity.GiftType
import co.eliseev.fingate.core.repository.GiftRepository
import co.eliseev.fingate.core.service.exception.GiftNotFoundException
import org.springframework.stereotype.Service

interface GiftService {
    fun getByType(giftType: GiftType): Gift
}

@Service
class GiftServiceImpl(private val giftRepository: GiftRepository): GiftService {

    override fun getByType(giftType: GiftType): Gift = giftRepository.getByGiftType(giftType)
        ?: throw GiftNotFoundException("Gift with type $giftType not found")

}
