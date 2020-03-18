package io.ssnc.ac.escort.service;

//import io.ssnc.ac.accessControl.entity.PCExceptionResult;
import io.ssnc.ac.escort.exception.NotFoundException
import io.ssnc.ac.escort.entity.*
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.entity.request.StoreRule
import io.ssnc.ac.escort.entity.response.IcatResult
import io.ssnc.ac.escort.exception.ServiceException
import io.ssnc.ac.escort.repository.*
import io.ssnc.ac.escort.service.model.*
import io.ssnc.ac.escort.util.DataUtil
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.Long.min

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.persistence.StoredProcedureQuery

@Service
class PCExceptionService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcIcatAppRepository: PcIcatAppRepository

    @Autowired
    lateinit var pcIcatExIpRepository: PcIcatExIpRepository

    @Autowired
    lateinit var pcIcatExHostRepository: PcIcatExHostRepository

    @Autowired
    lateinit var pcIcatClsidRepository: PcIcatClsidRepository

    @Autowired
    lateinit var pcIcatExpListRepository: PcIcatExpListRepository

    @Autowired
    lateinit var pcIcatExceptionRepository: PcIcatExceptionRepository

    @Autowired
    lateinit var policyService: PolicyService

    @Autowired
    lateinit var pcIcatRefService: PcIcatRefService

    @Autowired
    lateinit var logService: LogService

    @Autowired
    lateinit var storageService: StorageService

    @Autowired
    lateinit var deviceService: DeviceService

    @Autowired
    lateinit var programService: ProgramService

    fun searchPcIcat(serial: String) : IcatResult? {
        //정책 조회
        val cntrOnOff : String = policyService.checkDefaultPolicy(serial, "ICAT_ON_CTRL")
        val logginOnOff: String = policyService.checkDefaultPolicy(serial, "ICAT_ON_LOGGING")

        val basic = pcBasicRepository.findBySerial(serial)

        val result = IcatResult(serial = basic.serial,
            empno = basic.empno!!,
            hname = basic.hname!!,
            locatenm = basic.locatenm!!,
            pc_gubun = basic.pcGubun!!,
            ctrl_onoff = cntrOnOff,
            logging_onoff = logginOnOff,
            icat_base = if (cntrOnOff == "ON") searchPcIcatDefault() else null,
            icat_exception = searchPcIcatIndiviaulException(serial)
        )

        return result
    }

    fun searchPcIcatDefault(): ArrayList<IcatCtrlBase> {
        val resultBasic = ArrayList<IcatCtrlBase>()
        // IP
        pcIcatExIpRepository.findAll().map { it ->
            resultBasic.add(
                IcatCtrlBase(
                    ctrlGubun = "PERMIT",
                    expType = "IP",
                    expVal1 = DataUtil.IPStringToNumber(it.pk.ip),
                    expVal2 = it.ipEnd!!
                )
            )
        }
        // APP
        pcIcatAppRepository.findAll().map { it ->
            resultBasic.add(
                IcatCtrlBase(
                    ctrlGubun = "BLOCK",
                    expType = "MSGR",
                    expVal1 = it.pk!!.appexe,
                    expVal2 = ""
                )
            )
        }
        // Host
        pcIcatExHostRepository.findAll().map { it ->
            resultBasic.add(
                IcatCtrlBase(
                    ctrlGubun = "PERMIT",
                    expType = "HOST",
                    expVal1 = it.pk.host,
                    expVal2 = ""
                )
            )
        }
        return resultBasic
    }

    fun searchPcIcatIndiviaulException(serial: String) : ArrayList<IcatException>? {
        val resultException = ArrayList<IcatException>()
        val results = pcIcatExpListRepository.findByPkSerial(serial)
        if (results.isNullOrEmpty()) {
            pcIcatExceptionRepository.findBySerial(serial)?.let {result ->
                resultException.add(
                    IcatException(
                        serial = result.serial,
                        ctrlGubun = result.gubun!!,
                        expType = "ALL_PERMIT",
                        expVal1 = null,
                        expVal2 = null,
                        allowFromdate = result.allowFromdate!!,
                        allowTodate = result.allowTodate!!
                    )
                )
            }
        } else {
            results.map {
                resultException.add(
                    IcatException(
                        serial = it.pk.serial,
                        ctrlGubun = it.pcIcatExp!!.gubun!!,
                        expType = it.pk.gubun,
                        expVal1 = if (it.pk.gubun == "IP") DataUtil.IPStringToNumber(it.pk.value1) else it.pk.value1,
                        expVal2 = if (it.pk.gubun == "IP") DataUtil.IPStringToNumber(it.value2!!) else "",
                        allowFromdate = it.starttime!!,
                        allowTodate = it.endtime!!
                    )
                )
            }
        }
//        pcIcatExpListRepository.findByPkSerial(serial)?.let { results ->
//            results.map {
//                resultException.add(
//                    IcatException(
//                        serial = it.pk.serial,
//                        ctrlGubun = it.pcIcatExp!!.gubun!!,
//                        expType = it.pk.gubun,
//                        expVal1 = if (it.pk.gubun == "IP") DataUtil.IPStringToNumber(it.pk.value1) else it.pk.value1,
//                        expVal2 = if (it.pk.gubun == "IP") DataUtil.IPStringToNumber(it.value2!!) else "",
//                        allowFromdate = it.starttime!!,
//                        allowTodate = it.endtime!!
//                    )
//                )
//            }
//        } ?: run {
//            pcIcatExceptionRepository.findBySerial(serial)?.let {result ->
//                resultException.add(
//                    IcatException(
//                        serial = result.serial,
//                        ctrlGubun = result.gubun!!,
//                        expType = "ALL_PERMIT",
//                        expVal1 = null,
//                        expVal2 = null,
//                        allowFromdate = result.allowFromdate!!,
//                        allowTodate = result.allowTodate!!
//                    )
//                )
//            }
//        }
        return resultException
    }

    fun createAccessControls(request: AccessControlRequest) {
        if (!request.storages.isNullOrEmpty()){
            storageService.createStorage(request)
        }
        if (!request.programs.isNullOrEmpty()){
            programService.createPrograms(request)
        }
        if (!request.devices.isNullOrEmpty()){
            deviceService.createDevices(request)
        }
        if (!request.files.isNullOrEmpty()){
            createFiles(request)
        }
    }



    fun createFiles(request: AccessControlRequest) {
        val basic = pcBasicRepository.findBySerial(request.serial)

        request.files!!.forEach { file ->
            var gubun : String

            file.excepts!!.forEach { except ->
                val piel_gubuns: ArrayList<PcIcatExpListTypes> = ArrayList()

                piel_gubuns.add(PcIcatExpListTypes(gubun = except.subGubun, value1 = except.subValue1))
                //val ip_pk = IncopsPolicyPK(locatenm = basic.locatenm, pcGbun = basic.pcGubun, gubun = "ICAT_FILE_REP")

                // 파일 사이즈, 통제 관리 설정
                policyService.searchPolicy(request.serial, "ICAT_FILE_REP", "N")?.let { it ->
                    piel_gubuns.add(PcIcatExpListTypes(gubun = "REP", value1 = it.value2.toString()))
                }
                policyService.searchPolicy(request.serial, "ICAT_SIZELIMIT", "N")?.let { it ->
                    if ( it.value1 == "X" )
                        piel_gubuns.add(PcIcatExpListTypes(gubun = "SIZE", value1 = ""))
                    else
                        piel_gubuns.add(PcIcatExpListTypes(gubun = "SIZE", value1 = it.value2.toString()))
                }

                when(file.allowType) {
                    "B" -> {
                        gubun = if (request.allowLog == "N") "0" else "1"

                        when(except.subGubun) {
                            "MSGR" -> {
                                if (pcIcatAppRepository.findByPkAppexeAndRunning(except.subValue1, 1) == null)
                                    throw NotFoundException("data not found")
                            }
                            "CLSID" -> {
                                //todo 액티브 엑스 확인
                                pcIcatClsidRepository.findByPkClsidAndRunning(except.subValue1, 1).let {
                                    throw NotFoundException("data not found")
                                }
                            }
                            "IP" -> {
                                val check: (Boolean, Boolean) -> Boolean = { a, b -> a && b}
                                if (check(DataUtil.IPisValid(except.subValue1), DataUtil.IPisValid(except.subValue2)) == false ) {
                                    throw ServiceException("IP input fault")
                                }
                                except.subValue1 = DataUtil.validateIP(except.subValue1)
                                except.subValue2 = DataUtil.validateIP(except.subValue2!!)
                            }
                        }
                        piel_gubuns.forEach { piel_gubun ->
                            val piel_pk = PcIcatExpListPk(
                                serial = request.serial,
                                grpGubun = request.grpGubun,
                                gubun = piel_gubun.gubun, //except.subGubun,
                                value1 = if (piel_gubun.gubun=="IP") except.subValue1 else piel_gubun.value1!!
                            )
                            pcIcatExpListRepository.findByPk(piel_pk)?.let { exist ->
                                exist.starttime = file.allowStartDate.substring(0, 10)
                                exist.endtime = file.allowEndDate.substring(0, 10)
                                exist.regempno = request.regEmpno
                                exist.regdate = DateUtil.nowDateTimeString
                                exist.allowDesc = file.allowDesc
                                exist.value2 = if (piel_gubun.gubun == "IP") except.subValue2 else ""
                                pcIcatExpListRepository.save(exist)
                                //메신저 size/통제로깅 저장
                            } ?: run {
                                val new = PcIcatExpList(
                                    pk = piel_pk,
                                    starttime = file.allowStartDate.substring(0, 10),
                                    endtime = file.allowEndDate.substring(0, 10),
                                    regempno = request.regEmpno,
                                    regdate = DateUtil.nowDateTimeString,
                                    allowDesc = file.allowDesc,
                                    value2 = if (piel_gubun.gubun == "IP") except.subValue2 else "" )
                                pcIcatExpListRepository.save(new)
                            }
                        }
                    }
                    else -> {
                        gubun = if (request.allowLog == "N") "2" else "4"
                        //todo delete 처리
                    }
                }

                var actiongb : String = ""
                pcIcatExceptionRepository.findBySerialAndGrpGubun(request.serial, request.grpGubun) ?.let { exist ->
                    exist.allowDesc = file.allowDesc
                    exist.gubun = gubun
                    exist.allowFromdate = file.allowStartDate
                    exist.allowTodate = file.allowEndDate
                    exist.empno = (if (request.grpGubun == "P") basic.empno else null).toString()
                    exist.hname = (if (request.grpGubun == "P") basic.hname else null).toString()
                    exist.sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString()
                    exist.deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString()
                    exist.regempno = request.regEmpno
                    exist.regdate = DateUtil.nowDateTimeString
                    pcIcatExceptionRepository.save(exist)
                    actiongb = "U"
                } ?: run {
                    val new = PcIcatException( serial = request.serial, grpGubun = request.grpGubun, allowDesc = file.allowDesc, gubun = gubun,
                        allowFromdate = file.allowStartDate, allowTodate = file.allowEndDate,
                        empno = (if (request.grpGubun == "P") basic.empno else null).toString(),
                        hname = (if (request.grpGubun == "P") basic.hname else null).toString(),
                        sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString(),
                        deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString(),
                        regempno = request.regEmpno, regdate = DateUtil.nowDateTimeString )
                    pcIcatExceptionRepository.save(new)
                    actiongb = "I"
                }
                //todo incopsLog insert
                // 로그 디렉토리 적재
                val log = ExceptionLogData(
                    serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
                    regEmpno = request.regEmpno, allowStartDate = file.allowStartDate, allowEndDate = file.allowEndDate,
                    devName = "ICAT", allowDesc = file.allowDesc, poGubun = "EXC_WALL01", actiongb = actiongb,
                    gubun = gubun, poGubundtl = "ICAT", logVal = request.allowLog, allowVal = gubun, remark1=null, remark2=null)
                logService.createLog(log)
            }
        }
    }

//    val lessData: (String, String) -> String = { a, b -> min(a.toLong(), b.toLong()).toString() }
//    val check: (Boolean, Boolean) -> Boolean = { a, b -> a && b}
}
