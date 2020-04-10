package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.*
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.entity.request.StoreRule
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PcExceptionRepository
import io.ssnc.ac.escort.repository.PcRegUsbdeviceRepository
import io.ssnc.ac.escort.repository.PcRegUsbexceptionRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DataUtil
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeviceService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcExceptionRepository: PcExceptionRepository

    @Autowired
    lateinit var logService: LogService

    @Autowired
    lateinit var pcIcatRefService: PcIcatRefService

    @Autowired
    lateinit var programService: ProgramService

    @Autowired
    lateinit var storageService: StorageService

    @Autowired
    lateinit var mediaService: MediaService

    @Autowired
    lateinit var pcRegUsbdeviceRepository: PcRegUsbdeviceRepository

    @Autowired
    lateinit var pcRegUsbexceptionRepository: PcRegUsbexceptionRepository

    fun createDevices(request: AccessControlRequest) {
        var actiongb : String = ""

        val basic = pcBasicRepository.findBySerial(request.serial)

        request.devices!!.forEach { device ->
            device.allowType =
                when (device.allowType) {
                    "A" -> "0"
                    "B" -> "1"
                    "E" -> "2"
                    "T" -> "3"
                    else -> "0"
                }
            // 1. 기존 데이터 fetch
            val exits_pk = PcExceptionPk(serial = request.serial, gubun = device.devName)
            pcExceptionRepository.findByPk(exits_pk)?.let { exits ->
                exits.expDate = DateUtil.nowDateTimeString
                exits.expDesc = device.allowDesc
                exits.value1 = if (device.allowType == "Y") 0 else 1
                exits.allowFromdate = DataUtil.lessData(
                    device.allowEndDate,
                    device.allowStartDate
                ) // if ( device.allowEndDate!!.toLong() < device.allowStartDate.toLong() ) exits.allowFromdate else device.allowStartDate
                exits.allowTodate = device.allowEndDate
                exits.regEmpno = request.regEmpno
                exits.grpGubun = request.grpGubun
                actiongb = "U"
                pcExceptionRepository.save(exits)
            } ?: run {
                val new_pce = PcException(
                    pk = exits_pk,
                    expDate = DateUtil.nowDateTimeString,
                    expDesc = device.allowDesc,
                    value1 = device.allowType.toInt(),
                    allowFromdate = device.allowStartDate,
                    allowTodate = device.allowEndDate,
                    regEmpno = request.regEmpno,
                    grpGubun = request.grpGubun
                )
                actiongb = "I"
                pcExceptionRepository.save(new_pce)
            }

            // 로그 디렉토리 적재
            val log = ExceptionLogData(
                serial = request.serial,
                grpGubun = request.grpGubun,
                allowLog = request.allowLog,
                regEmpno = request.regEmpno,
                allowStartDate = device.allowStartDate,
                allowEndDate = device.allowEndDate,
                devName = device.devName,
                allowDesc = device.allowDesc,
                poGubun = when (device.devName) {
                    "B", "S", "F", "V", "E", "5", "6", "7", "8", "X" -> "EXC_CLINIC21"
                    else -> "EXC_WALL01"
                },
                actiongb = actiongb,
                gubun = device.devName,
                poGubundtl = device.devName,
                logVal = request.allowLog,
                allowVal = device.allowType,
                remark1 = null,
                remark2 = null
            )
            logService.createLog(log)

            if (device.devName == "M") {
                val storeRule = StoreRule(
                    serial = request.serial,
                    grpGubun = request.grpGubun, allowLog = request.allowLog, regEmpno = request.regEmpno,
                    allowStartDate = device.allowStartDate, allowEndDate = device.allowEndDate,
                    allowType = when (device.allowType) {
                        "0" -> "Y"
                        else -> "N"
                    },
                    pgmLists = device.pgmLists,
                    allowDesc = device.allowDesc, devName = "M"
                )
                storageService.createStorage1(storeRule)
                if (!device.pgmLists.isNullOrEmpty()) {
                    programService.createPgm(storeRule)
                    storeRule.devName = "PDA"
                    mediaService.createMedia(storeRule)
                }
            }
        }
    }

    fun searchPcExceptionBySerial(serial: String) : List<PcException>? {
        return pcExceptionRepository.findByPkSerial(serial)
    }

    fun createUsbDevice(pcRegUsbdevice: PcRegUsbdevice) {
        pcRegUsbdeviceRepository.save(pcRegUsbdevice)
    }

    fun searchUsbExceptionBySerial(serial: String) : List<PcRegUsbexception>? {
        return pcRegUsbexceptionRepository.findByPkSerial(serial)
    }
}