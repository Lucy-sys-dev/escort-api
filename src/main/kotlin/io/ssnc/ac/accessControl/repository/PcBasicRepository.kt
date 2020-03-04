package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.PcBasic
import io.ssnc.ac.accessControl.entity.PcException
import io.ssnc.ac.accessControl.entity.PcExceptionPk
import io.ssnc.ac.accessControl.entity.PfwStoreRulePk
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PcBasicRepository: CrudRepository<PcBasic, String> {
    fun findBySerial(serial: String): PcBasic
}

@Repository
interface PcExceptionRepository: CrudRepository<PcException, String> {
    fun findByPk(pk: PcExceptionPk) : PcException?
}