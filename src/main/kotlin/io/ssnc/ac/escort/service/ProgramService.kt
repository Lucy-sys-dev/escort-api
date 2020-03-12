package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.PcPgmException
import io.ssnc.ac.escort.entity.PcPgmExceptionPk
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.entity.request.StoreRule
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PcPgmExceptionRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProgramService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcPgmExceptionRepository: PcPgmExceptionRepository

    @Autowired
    lateinit var pcIcatRefService: PcIcatRefService

    @Autowired
    lateinit var logService: LogService

    fun createPrograms(request: AccessControlRequest) {
        request.programs!!.forEach { program ->
            //
            if (program.allowType == "A") program.allowType = "0"

        }
    }

    fun createPgm(request: StoreRule) {
        var actiongb: String =""
        val basic = pcBasicRepository.findBySerial(request.serial)

        request.pgmLists!!.forEach { filename ->
            pcIcatRefService.searchPcPgmListbyFileName(filename)?.let { pgm ->
                val pce_pk = PcPgmExceptionPk(seq = pgm.seq, serial = request.serial)
                pcPgmExceptionRepository.findByPk(pce_pk)?.let { exist ->
                    when (request.allowType) {
                        "1" -> {
                            pcPgmExceptionRepository.deleteByPk(pce_pk)
                            actiongb = "D"
                        }
                        else -> {
                            exist.expDate = DateUtil.nowDateTimeString
                            exist.expDesc = request.allowDesc
                            exist.allowFromdate = request.allowStartDate
                            exist.allowTodate = request.allowEndDate
                            exist.grpGubun = request.grpGubun
                            exist.regEmpno = request.regEmpno
                            pcPgmExceptionRepository.save(exist)
                            actiongb = "U"
                        }
                    }
                } ?: run {
                    if (request.allowType != "1") {
                        val new = PcPgmException(
                            pk = pce_pk,
                            expDate = DateUtil.nowDateTimeString,
                            expDesc = request.allowDesc,
                            allowTodate = request.allowEndDate,
                            allowFromdate = request.allowStartDate,
                            regEmpno = request.regEmpno,
                            grpGubun = request.grpGubun
                        )
                        pcPgmExceptionRepository.save(new)
                        actiongb = "I"
                    }
                }

                val log = ExceptionLogData(
                    serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
                    regEmpno = request.regEmpno, allowStartDate = request.allowStartDate, allowEndDate = request.allowEndDate,
                    devName = pgm.pgmName!!, allowDesc = request.allowDesc,
                    poGubun = "EXC_WALL02", actiongb = actiongb, gubun = "",
                    poGubundtl = "PUBLIC_PRGM", logVal = request.allowLog, allowVal = request.allowType, remark1 = pgm.pgmName, remark2 = pgm.fileName)
                logService.createLog(log)

            } ?: run {

            }
        }
    }
}