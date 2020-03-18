package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.IncopsPcexceptionLog
import io.ssnc.ac.escort.entity.IncopsPcexceptionLogPK
import io.ssnc.ac.escort.repository.IncopsPcexceptionLogRepository
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LogService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var incopsPcexceptionLogRepository: IncopsPcexceptionLogRepository

    fun createLog(request: ExceptionLogData) {
        val basic = pcBasicRepository.findBySerial(request.serial)

        val log_pk = IncopsPcexceptionLogPK(changeTime = DateUtil.nowDateTimeString,
            actiongb = request.actiongb, serial = request.serial, poGubun = request.poGubun, devName = request.devName)

        val log = IncopsPcexceptionLog(pk = log_pk,
            empno = (if (request.grpGubun == "P") basic.empno else null).toString(),
            hname = (if (request.grpGubun == "P") basic.hname else null).toString(),
            poGubundtl = request.poGubundtl,
            sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString(),
            deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString(),
            indeptnm = (if (request.grpGubun == "P") basic.indeptnm else null).toString(),
            locatenm = (if (request.grpGubun == "P") basic.locatenm else null).toString(),
            pcGubun = (if (request.grpGubun == "P") basic.pcGubun else null).toString(),
            grpGubun = request.grpGubun,
            allowedDate = DateUtil.nowDateTimeString,
            allowedDesc = request.allowDesc,
            allowFromdate = request.allowStartDate,
            allowTodate = request.allowEndDate,
            ruleNo = null,
            portName = null,
            gubun = request.gubun,
            allowVal = request.gubun,
            logVal = request.allowLog,
            changer = request.regEmpno,
            remark1 = null, remark2 = null
        )
        //log 저장
        incopsPcexceptionLogRepository.save(log)
    }
}