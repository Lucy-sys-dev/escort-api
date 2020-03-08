package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.PcBasic
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PcBasicRepository: CrudRepository<PcBasic, String> {
    fun findBySerial(serial: String): PcBasic
}
