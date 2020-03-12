package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.SsaClaastackVerinfo
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SsaClassstackVerinfoRepository: CrudRepository<SsaClaastackVerinfo, String> {
    //fun findFirstByRegDateDesc() : SsaClaastackVerinfo
    //abstract fun findOrderByRegDate(): List<SsaClaastackVerinfo>
}