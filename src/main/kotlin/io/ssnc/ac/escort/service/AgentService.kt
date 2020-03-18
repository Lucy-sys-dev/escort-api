package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.Log
import io.ssnc.ac.escort.entity.LogPK
import io.ssnc.ac.escort.entity.SsaClaastackVerinfo
import io.ssnc.ac.escort.entity.request.LogRequest
import io.ssnc.ac.escort.entity.request.TeminateRequest
import io.ssnc.ac.escort.exception.NotFoundException
import io.ssnc.ac.escort.exception.ServiceException
import io.ssnc.ac.escort.repository.*
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.io.File
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.StoredProcedureQuery
import javax.transaction.Transactional

@Service
class AgentService {
    companion object : KLogging()

    @Autowired
    lateinit var ssaClassstackVerinfoRepository: SsaClassstackVerinfoRepository

    @Autowired
    lateinit var logRepository: LogRepository

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcTeminateRepository: PcTeminateRepository

    @Autowired
    lateinit var pcExitpwRepository: PcExitpwRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Transactional
    fun getVersion(): SsaClaastackVerinfo? {
        val sortedByRegDateDesc = Sort(Sort.Direction.DESC, "REG_DATE")
        val results = ssaClassstackVerinfoRepository.findAll()
        //return datas.first()
        return results.last()//.first()
    }

    fun checkFile(file: File) {
        if (!file.isFile) {
            throw IllegalStateException("The path '${file.path}' is not a valid file")
        }
    }

    @Transactional
    fun createLog(request: LogRequest) : String? {
        PCExceptionService.logger.info("Method=create, createLog={}", request)
        val logpk = LogPK(
            eventTime = DateUtil.nowDateTimeString,
            serial = request.serial, type = request.type, attFilename = request.att_filename)
        val pcbasic = pcBasicRepository.findBySerial(request.serial)
        val log = Log(logPk = logpk,
            empno = pcbasic.empno, hname = pcbasic.hname, ip = pcbasic.ipAddr,
            sdeptnm = pcbasic.sdeptnm, deptcode = pcbasic.deptcode, locatenm = pcbasic.locatenm, madecode = pcbasic.madecode)
        logRepository.save(log)

        return log.logPk!!.eventTime
    }

    fun teminate(request: TeminateRequest) {

        val pcExitpw = pcExitpwRepository.findFirstByOrderByEventTime()

//        val query: StoredProcedureQuery = entityManager
//            .createNamedStoredProcedureQuery("getTerminatePassword")
//            .setParameter("P_STR", request.password)
//            .setParameter("P_KEY", pcExitpw.passwdKey)
//
//        val result : String = query.resultList.get(0) as String
////        if (result.key.isNullOrEmpty()) {
////            throw NotFoundException("password encoder error")
////        }

//        val query: Query = entityManager.createNativeQuery("SET NOCOUNT ON; exec dbo.UFN_ENCRYPT '1234', '2783096541';")
////        query.setParameter("P_STR", request.password)
////        query.setParameter("P_KEY", pcExitpw.passwdKey)
//        val result = query.getSingleResult() // as String

        val result = pcTeminateRepository.getTerminatePassword(request.password, pcExitpw.passwdKey) as String

        if (result != pcExitpw.passwd) {
            throw ServiceException("password doesn't match")
        }
    }
}