package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface PcExceptionRepository: CrudRepository<PcException, String> {
    fun findByPk(pk: PcExceptionPk) : PcException?
}

@Repository
interface PcPgmExceptionRepository: CrudRepository<PcPgmException, String> {
    fun findByPk(pk: PcPgmExceptionPk) : PcPgmException?
    fun deleteByPk(pk: PcPgmExceptionPk) : Long
}

@Repository
interface PcPgmListRepository: CrudRepository<PcPgmList, String> {
    fun findTopByFileName(fileName: String) : PcPgmList?
}