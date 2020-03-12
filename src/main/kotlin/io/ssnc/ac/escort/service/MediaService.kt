package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.PfwMediaException
import io.ssnc.ac.escort.entity.PfwMediaExceptionPk
import io.ssnc.ac.escort.entity.PfwMediaRulePk
import io.ssnc.ac.escort.entity.request.StoreRule
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PfwMediaExceptionRepository
import io.ssnc.ac.escort.repository.PfwMediaRuleRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MediaService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pfwMediaRuleRepository: PfwMediaRuleRepository

    @Autowired
    lateinit var pfwMediaExceptionRepository: PfwMediaExceptionRepository

    @Autowired
    lateinit var logService: LogService

    fun createMedia(request: StoreRule) {
        var actiongb: String =""
        val basic = pcBasicRepository.findBySerial(request.serial)

        request.pgmLists!!.forEach { filename ->
            val pmr_pk = PfwMediaRulePk(gubun = request.devName, procName = filename)
            pfwMediaRuleRepository.findByPk(pmr_pk)?.let {
                val pme_pk = PfwMediaExceptionPk(gubun = request.devName, serial = request.serial, procName = filename)
                pfwMediaExceptionRepository.findByPk(pme_pk)?.let { exist ->
                    when(request.allowType) {
                        "1" -> {
                            pfwMediaExceptionRepository.deleteByPk(pme_pk)
                            actiongb = "D"
                        }
                        else -> {
                            exist.regDate = DateUtil.nowDateTimeString
                            exist.allowDesc = request.allowDesc
                            exist.allowFromdate = request.allowStartDate
                            exist.allowTodate = request.allowEndDate
                            exist.allowGubun = if (request.allowType == "1") 0 else 1
                            exist.grpGubun = request.grpGubun
                            exist.regEmpno = request.regEmpno
                            pfwMediaExceptionRepository.save(exist)
                            actiongb = "U"
                        }
                    }
                } ?: run {
                    if (request.allowType != "1") {
                        val new = PfwMediaException(
                            pk = pme_pk,
                            regDate = DateUtil.nowDateTimeString,
                            allowDesc = request.allowDesc,
                            allowTodate = request.allowEndDate,
                            allowFromdate = request.allowStartDate,
                            allowGubun = if (request.allowType == "1") 0 else 1,
                            regEmpno = request.regEmpno,
                            grpGubun = request.grpGubun
                        )
                        pfwMediaExceptionRepository.save(new)
                        actiongb = "I"
                    }
                }
                val log = ExceptionLogData(
                    serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
                    regEmpno = request.regEmpno, allowStartDate = request.allowStartDate, allowEndDate = request.allowEndDate,
                    allowDesc = request.allowDesc, actiongb = actiongb, logVal = request.allowLog, allowVal = request.allowType,
                    devName = pme_pk.gubun!!, poGubun = "EXC_WALL03",  gubun = "", poGubundtl = "DEVICE_PRGM",  remark1 = pme_pk.procName, remark2 = null)
                logService.createLog(log)
            }
        }
    }
}