package io.ssnc.ac.accessControl.service;

//import io.ssnc.ac.accessControl.entity.PCExceptionResult;
import io.ssnc.ac.accessControl.controller.NotFoundException
import io.ssnc.ac.accessControl.entity.*
import io.ssnc.ac.accessControl.entity.request.LogRequest
import io.ssnc.ac.accessControl.entity.response.IcatResult
import io.ssnc.ac.accessControl.repository.*
import io.ssnc.ac.accessControl.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.io.File

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.persistence.StoredProcedureQuery

@Service
class PCExceptionService {
    companion object : KLogging()

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var pcIcatBasicRepository: PCIcatBasicRepository

    @Autowired
    lateinit var pcIcatDefaultRepository: PCIcatDefaultRepository

    @Autowired
    lateinit var logRepository: LogRepository

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var ssaClassstackVerinfoRepository: SsaClassstackVerinfoRepository

    @Transactional
    fun getVersion(): SsaClaastackVerinfo {
        var results = ssaClassstackVerinfoRepository.findAll()
        return results.last()
    }

    @Transactional
    fun search(serial: String) : IcatResult? {

        val query: StoredProcedureQuery = entityManager
            .createNamedStoredProcedureQuery("getIcatCtrlDefault")
            .setParameter("serial", serial)

        val defaultResult : IcatCtrlDefault = query.resultList.get(0) as IcatCtrlDefault
        if (defaultResult.empno.isNullOrEmpty()) {
            throw NotFoundException("empno is null")
        }

        val resultBasic = ArrayList<IcatCtrlBase>()

        if (defaultResult.ctrlOnoff.equals("ON")) {
            val query1: StoredProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("getIcatCtrlBasic")

            val basics = query1.resultList

            basics.forEach { it ->
                val icb = it as IcatCtrlBase
                val basic = IcatCtrlBase(ctrlGubun = icb.ctrlGubun, expType = icb.expType, expVal1 = icb.expVal1, expVal2 = icb.expVal2)
                resultBasic.add(basic)
            }
        }
        val resultException = ArrayList<IcatException>()
        val query2: StoredProcedureQuery = entityManager
            .createNamedStoredProcedureQuery("getIcatException")
            .setParameter("serial", serial)

        val exceptions = query2.resultList

        exceptions.forEach { it ->
            val ie = it as IcatException
            val exception = IcatException(serial = ie.serial, ctrlGubun = ie.ctrlGubun,
                                          expType = ie.expType, expVal1 = ie.expVal1, expVal2 = ie.expVal2,
                                          allowFromdate = ie.allowFromdate, allowTodate = ie.allowTodate)
            resultException.add(exception)
        }


        val result = IcatResult(serial = defaultResult.serial,
            empno = defaultResult.empno,
            hname = defaultResult.hname,
            locatenm = defaultResult.locatenm,
            pc_gubun = defaultResult.pcGubun,
            ctrl_onoff = defaultResult.ctrlOnoff,
            logging_onoff = defaultResult.loggingOnoff,
            icat_base = resultBasic,
            icat_exception = resultException)

        return result
    }

    @Transactional
    fun createLog(request: LogRequest) : String? {
        logger.info("Method=create, createLog={}", request)
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

    fun checkFile(file: File) {
        if (!file.isFile) {
            throw IllegalStateException("The path '${file.path}' is not a valid file")
        }
    }
}
