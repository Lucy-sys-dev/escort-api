package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.*
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface PcExceptionRepository: CrudRepository<PcException, String> {
    fun findByPk(pk: PcExceptionPk) : PcException?
    fun findByPkAndAllowTodateGreaterThan(pk: PcExceptionPk, date: String): PcException?
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
interface PcIcatExIpRepository: CrudRepository<PcIcatExIp, String> {
    fun findByPk(pk: PcIcatExIpPk): PcIcatExIp?
}

@Repository
interface PcIcatExHostRepository: CrudRepository<PcIcatExHost, String> {
    fun findByPk(pk: PcIcatExHostPk): PcIcatExHost?
}

@Repository
interface PcIcatClsidRepository: CrudRepository<PcIcatClsid, String> {
    fun findByPk(pk: PcIcatClsidPk) : PcIcatClsid?
    fun findByPkClsidAndRunning(clsid: String, running: Int) : PcIcatClsid?
}

@Repository
interface PcIcatExpListRepository: CrudRepository<PcIcatExpList, String> {
    fun findByPk(pk: PcIcatExpListPk): PcIcatExpList?
    fun findByPkSerial(serial: String) : List<PcIcatExpList>?
}

@Repository
interface PcIcatExceptionRepository: CrudRepository<PcIcatException, String> {
    fun findBySerial(serial: String): PcIcatException?
    fun findBySerialAndGrpGubun(serial: String, grpGubun: String?): PcIcatException?
    //fun findBySerialAndGubunIn()
}

@Repository
interface PcExceptionMultiRepository: CrudRepository<PcExceptionMulti, String> {
    fun deleteByPkSerialAndPkGubunAndPkAllowTodateLessThan(serial: String, gubun: String, date: String): Long
    fun findByPkAndGrpGubun(pk: PcExceptionMultiPk, grpGubun: String) : PcExceptionMulti?
    fun findByPkSerialAndPkGubunAndPkAllowFromdateLessThanAndPkAllowTodateGreaterThanAndGrpGubun(
        serial: String, gubun: String, allowFromDate: String, allowToDate: String, grpGubun: String, sort: Sort) : List<PcExceptionMulti>?
}

@Repository
interface PcExceptionMultiLogRepository: CrudRepository<PcExceptionMultiLog, String> {

}