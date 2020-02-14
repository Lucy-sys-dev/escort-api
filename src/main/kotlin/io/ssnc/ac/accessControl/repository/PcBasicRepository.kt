package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.PcBasic
import org.springframework.data.repository.CrudRepository

interface PcBasicRepository: CrudRepository<PcBasic, String> {
    fun findBySerial(serial: String): PcBasic
}