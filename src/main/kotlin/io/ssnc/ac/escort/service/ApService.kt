package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.*
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.repository.PcExceptionMultiLogRepository
import io.ssnc.ac.escort.repository.PcExceptionMultiRepository
import io.ssnc.ac.escort.repository.PcExceptionRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ApService {
    companion object : KLogging()

    @Autowired
    lateinit var pcExceptionMultiRepository: PcExceptionMultiRepository

    @Autowired
    lateinit var pcExceptionRepository: PcExceptionRepository

    @Autowired
    lateinit var pcExceptionMultiLogRepository: PcExceptionMultiLogRepository

    @Autowired
    lateinit var logService: LogService

    fun createAps(request: AccessControlRequest) {
        request.aps!!.forEach { ap ->
            pcExceptionMultiRepository.deleteByPkSerialAndPkGubunAndPkAllowTodateLessThan(request.serial, ap.devName, ap.allowEndDate)
            val pk = PcExceptionPk(serial = request.serial, gubun = ap.devName)
            pcExceptionRepository.findByPkAndAllowTodateGreaterThan(pk, ap.allowEndDate)?.let { exist ->
                pcExceptionMultiRepository.findByPkAndGrpGubun(pk = PcExceptionMultiPk(serial = request.serial, gubun = ap.devName, value1 = exist.value1!!, allowFromdate = exist.allowFromdate!!, allowTodate = exist.allowTodate!!), grpGubun = "P")?.let {

                } ?: run {
                    val new = PcExceptionMulti(pk = PcExceptionMultiPk(serial = request.serial, gubun = ap.devName, value1 = exist.value1!!, allowFromdate = exist.allowFromdate!!, allowTodate = exist.allowTodate!!),
                        expDesc = exist.expDesc, expDate = exist.expDate, regEmpno = exist.regEmpno, grpGubun = "P")
                    pcExceptionMultiRepository.save(new)
                }
            }
            val value = when (ap.allowType) {
                "A" -> {
                    if (request.allowLog == "N") "4"
                    else "6"
                }
                else -> {
                    if (request.allowLog == "N") "1"
                    else "3"
                }
            }
            pcExceptionMultiRepository.findByPkAndGrpGubun(pk = PcExceptionMultiPk(serial = request.serial, gubun = ap.devName, value1 = value.toInt(), allowFromdate = ap.allowStartDate, allowTodate = ap.allowEndDate), grpGubun = "P")?.let { exist ->
                exist.expDesc = ap.allowDesc
                exist.expDate = DateUtil.nowDateTimeString
                exist.regEmpno = request.regEmpno
                exist.grpGubun = "P"
                pcExceptionMultiRepository.save(exist)

            } ?: run {
                val new = PcExceptionMulti(pk = PcExceptionMultiPk(serial = request.serial, gubun = ap.devName, value1 = value.toInt(), allowFromdate = ap.allowStartDate, allowTodate = ap.allowEndDate),
                    expDesc = ap.allowDesc, expDate = DateUtil.nowDateTimeString, regEmpno = request.regEmpno, grpGubun = "P")
                pcExceptionMultiRepository.save(new)
            }

            // pc-exception insert
            var actiongb: String = ""
            val sortedByAllowToDateDesc = Sort(Sort.Direction.DESC, "ALLOW_TODATE", "EXP_DATE")
            pcExceptionMultiRepository.findByPkSerialAndPkGubunAndPkAllowFromdateLessThanAndPkAllowTodateGreaterThanAndGrpGubun(
                serial = request.serial, gubun = ap.devName, allowFromDate = ap.allowStartDate, allowToDate = ap.allowEndDate, grpGubun = "P", sort = sortedByAllowToDateDesc)
                ?.let { its ->
                    val it = its.first()
                    pcExceptionRepository.findByPk(pk = PcExceptionPk(serial = it.pk.serial, gubun = ap.devName))?.let { exist ->
                        val old : PcException = exist
                        exist.value1 = value.toInt()
                        exist.expDate = DateUtil.nowDateTimeString
                        exist.expDesc = ap.allowDesc
                        exist.allowFromdate = ap.allowStartDate
                        exist.allowTodate = ap.allowEndDate
                        exist.grpGubun = "P"
                        pcExceptionRepository.save(exist)
                        val new : PcException = exist
                        createPcExceptionMultiLog(old, new)
                        actiongb = "U"
                    } ?: run {
                        val new = PcException(pk = PcExceptionPk(serial = it.pk.serial, gubun = ap.devName),
                            value1 = value.toInt(), expDate = DateUtil.nowDateTimeString, expDesc = ap.allowDesc,
                            allowFromdate = ap.allowStartDate, allowTodate = ap.allowEndDate, grpGubun = "P")
                        pcExceptionRepository.save(new)
                        createPcExceptionMultiLog(null, new)
                        actiongb = "I"
                    }
                }
            // todo pc_exception_log insert
            // todo incops log 저장
            val log = ExceptionLogData(
                serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
                regEmpno = request.regEmpno, allowStartDate = ap.allowStartDate, allowEndDate = ap.allowEndDate,
                allowDesc = ap.allowDesc, actiongb = actiongb, logVal = request.allowLog, allowVal = value,
                devName = ap.devName, poGubun = "EXC_WALL01",  gubun = ap.devName, poGubundtl = ap.devName,  remark1 = null, remark2 = null)
            logService.createLog(log)

        //end AP(s)
        }
    }

    fun createPcExceptionMultiLog(old: PcException?, new: PcException) {
        val new = PcExceptionMultiLog(
            pk = PcExceptionMultiLogPk(logTime = DateUtil.nowDateTimeString, serial = new.pk!!.serial!!, gubun = new.pk.gubun!!),
            oldExpDate = old?.let { old.expDate },
            oldExpDesc = old?.let { old.expDesc },
            oldAllowFromdate = old?.let { old.allowFromdate },
            oldAllowTodate = old?.let { old.allowTodate },
            oldRegEmpno = old?.let { old.regEmpno },
            oldGrpGubun = old?.let { old.grpGubun },
            newExpDate =  new.expDate,
            newExpDesc = new.expDesc,
            newAllowFromdate = new.allowFromdate,
            newAllowTodate = new.allowTodate,
            newRegEmpno = new.regEmpno,
            newGrpGubun = new.grpGubun  )
        pcExceptionMultiLogRepository.save(new)
    }
}