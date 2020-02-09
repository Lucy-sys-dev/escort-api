package io.ssnc.ac.accessControl.service;

//import io.ssnc.ac.accessControl.entity.PCExceptionResult;
import io.ssnc.ac.accessControl.controller.NotFoundException
import io.ssnc.ac.accessControl.entity.IcatCtrlBase
import io.ssnc.ac.accessControl.entity.IcatCtrlDefault
import io.ssnc.ac.accessControl.entity.Log
import io.ssnc.ac.accessControl.entity.LogPK
import io.ssnc.ac.accessControl.entity.request.LogRequest
import io.ssnc.ac.accessControl.entity.response.IcatResult
import io.ssnc.ac.accessControl.repository.LogRepository
import io.ssnc.ac.accessControl.repository.PCIcatBasicRepository;
import io.ssnc.ac.accessControl.repository.PCIcatDefaultRepository;
import io.ssnc.ac.accessControl.util.DateUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import javax.persistence.StoredProcedureQuery

@Service
class PCExceptionService {
//    companion object : KLogging()

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var pcIcatBasicRepository: PCIcatBasicRepository

    @Autowired
    lateinit var pcIcatDefaultRepository: PCIcatDefaultRepository

    @Autowired
    lateinit var logRepository: LogRepository

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

        if (defaultResult.ctrlOnoff.equals("OFF")) {
            val query1: StoredProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("getIcatCtrlBasic")

            val basics = query1.resultList

            basics.forEach { it ->
                val icb = it as IcatCtrlBase
                val basic = IcatCtrlBase(ctrlGubun = icb.ctrlGubun, expType = icb.expType, expVal1 = icb.expVal1, expVal2 = icb.expVal2)
                resultBasic.add(basic)
            }
        }
        val result = IcatResult(serial = defaultResult.serial,
            empno = defaultResult.empno,
            hname = defaultResult.hname,
            locatenm = defaultResult.locatenm,
            pc_gubun = defaultResult.pcGubun,
            ctrl_onoff = defaultResult.ctrlOnoff,
            logging_onoff = defaultResult.loggingOnoff,
            icat_base = resultBasic)

        return result
    }

    @Transactional
    fun createLog(request: LogRequest) : String? {
//        logger.info("Method=create, product={}", createProductRequest)
        val logpk = LogPK(eventTime = DateUtil.nowDateTime, serial = request.serial, type = request.type, attFilename = request.att_filename)
        val log = Log(logPk = logpk)
        logRepository.save(log)

        return log.logPk!!.eventTime
    }
}
