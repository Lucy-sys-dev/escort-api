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

@Repository
interface PfwMediaExceptionRepository: CrudRepository<PfwMediaException, String> {
    fun findByPk(pk: PfwMediaExceptionPk): PfwMediaException?
    fun deleteByPk(pk: PfwMediaExceptionPk) : Long
}

@Repository
interface PfwMediaRuleRepository: CrudRepository<PfwMediaRule, String> {
    fun findByPk(pk: PfwMediaRulePk) : PfwMediaRule?
}

@Repository
interface PcIcatAppRepository: CrudRepository<PcIcatApp, String> {
    fun findByPk(pk: PcIcatAppPk): PcIcatApp?
    fun findByPkAppexeAndRunning(appexe: String, running: Int): PcIcatApp?
}

@Repository
interface PcIcatClsidRepository: CrudRepository<PcIcatClsid, String> {
    fun findByPk(pk: PcIcatClsidPk) : PcIcatClsid?
}