package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.PcException
import io.ssnc.ac.escort.entity.PcExceptionPk
import io.ssnc.ac.escort.entity.PfwStoreRule
import io.ssnc.ac.escort.entity.PfwStoreRulePk
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.entity.request.StoreRule
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PcExceptionRepository
import io.ssnc.ac.escort.repository.PfwStoreRuleRepository
import io.ssnc.ac.escort.service.model.ExceptionLogData
import io.ssnc.ac.escort.util.DataUtil
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StorageService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pfwStoreRuleRepository: PfwStoreRuleRepository

    @Autowired
    lateinit var pcExceptionRepository: PcExceptionRepository

    @Autowired
    lateinit var logService: LogService

    @Autowired
    lateinit var policyService: PolicyService

    fun createStorage(request: AccessControlRequest) {

        //정책 조회
        var storeNoUse : Int? = policyService.searchPolicy("ALL", "STORE_NOUSE", "Y")?.let { it -> it.value2!! }
//        val ip_pk = IncopsPolicyPK(locatenm = "ALL", pcGbun = "Z", gubun = "STORE_NOUSE")
//        val policy = incopsPolicyRepository.findByPk(ip_pk)
//        storeNoUse = policy.value2!!

        val basic = pcBasicRepository.findBySerial(request.serial)

        request.storages!!.forEach { storage ->
            if (storage.devName.equals("MOBILE-RW")) {
                when (storage.allowType) {
                    "A", "R" -> storage.allowType = "Y"
                    "B" -> storage.allowType = "N"
                }
                storeNoUse = 0
            }

            //기존 정책 등록 여부 확인
            var actiongb : String = ""
            val psr_pk = PfwStoreRulePk(serial = request.serial, devName = storage.devName)

            pfwStoreRuleRepository.findByPkAndGrpGubun(psr_pk, "P")?.let { exits ->
                exits.allowType = storage.allowType
                exits.allowLog = request.allowLog
                exits.allowDesc = storage.allowDesc
                exits.allowFromdate = DataUtil.lessData(exits.allowFromdate!!, storage.allowStartDate) //if ( exits.allowFromdate!!.toLong() < storage.allowStartDate.toLong() ) exits.allowFromdate else storage.allowStartDate
                exits.allowTodate = storage.allowEndDate
                exits.storeNouse = storeNoUse
                exits.allowDate = DateUtil.nowDateToYYYYMMDD
                exits.regEmpno = request.regEmpno
                exits.deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString()
                exits.lastuseTime = DateUtil.nowDateToYYYYMMDDHHMM

                pfwStoreRuleRepository.save(exits)
                actiongb = "U"
            } ?: run {
                val new = PfwStoreRule(pk = psr_pk,
                    empno = (if (request.grpGubun == "P") basic.empno else null).toString(),
                    hname = (if (request.grpGubun == "P") basic.hname else null).toString(),
                    sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString(),
                    indeptnm = (if (request.grpGubun == "P") basic.indeptnm else null).toString(),
                    locatenm = (if (request.grpGubun == "P") basic.locatenm else null).toString(),
                    allowType = storage.allowType,
                    allowLog = request.allowLog,
                    allowDesc = storage.allowDesc,
                    allowFromdate = storage.allowStartDate,
                    allowTodate = storage.allowEndDate,
                    storeNouse = storeNoUse,
                    allowDate = DateUtil.nowDateToYYYYMMDD,
                    regEmpno = request.regEmpno,
                    deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString(),
                    lastuseTime = DateUtil.nowDateToYYYYMMDDHHMM ,
                    grpGubun = request.grpGubun,
                    removalAllow = null
                )
                pfwStoreRuleRepository.save(new)
                actiongb = "I"
            }

            // 로그 디렉토리 적재
            val log = ExceptionLogData(
                serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
                regEmpno = request.regEmpno, allowStartDate = storage.allowStartDate, allowEndDate = storage.allowEndDate,
                devName = storage.devName, allowDesc = storage.allowDesc, poGubun = "EXC_WALL01", actiongb = actiongb,
                gubun = storeNoUse.toString(), poGubundtl = storage.devName, logVal = request.allowLog, allowVal = storage.allowType, remark1=null, remark2=null)
            logService.createLog(log)

            //MOBILE 장치 처리 추가
            if (storage.devName.equals("MOBILE-RW")) {
                val mobileTypes: Array<String> = arrayOf("T", "M", "9")
                for (i in mobileTypes.indices) {
                    val pce_pk = PcExceptionPk(serial = request.serial, gubun = mobileTypes[i])
                    pcExceptionRepository.findByPk(pce_pk)?.let { exits_pc ->
                        exits_pc.expDate = DateUtil.nowDateTimeString
                        exits_pc.expDesc = storage.allowDesc
                        exits_pc.value1 = if (storage.allowType == "Y") 0 else 1
                        exits_pc.allowFromdate = DataUtil.lessData(exits_pc.allowFromdate!!, storage.allowStartDate) // if ( exits_pc.allowFromdate!!.toLong() < storage.allowStartDate.toLong() ) exits_pc.allowFromdate else storage.allowStartDate
                        exits_pc.allowTodate = storage.allowEndDate
                        exits_pc.regEmpno = request.regEmpno
                        exits_pc.grpGubun = request.grpGubun
                        actiongb = "U"

                        pcExceptionRepository.save(exits_pc)
                    } ?: run {
                        val new_pce = PcException(pk = pce_pk,
                            expDate = DateUtil.nowDateTimeString,
                            expDesc = storage.allowDesc,
                            value1 = if (storage.allowType == "Y") 0 else 1,
                            allowFromdate = storage.allowStartDate,
                            allowTodate = storage.allowEndDate,
                            regEmpno = request.regEmpno,
                            grpGubun = request.grpGubun
                        )
                        actiongb = "I"
                        pcExceptionRepository.save(new_pce)
                    }
                    //log 저장
                    log.devName = mobileTypes[i]
                    log.actiongb = actiongb
                    log.poGubundtl = mobileTypes[i]
                    log.gubun = mobileTypes[i]
                    log.allowVal = (if (storage.allowType == "Y") 0 else 1).toString()
                    logService.createLog(log)
                }
            }
        }
    }

    fun createStorage1(request: StoreRule) {
        //정책 조회
//        var storeNoUse = 0
//        val ip_pk = IncopsPolicyPK(locatenm = "ALL", pcGbun = "Z", gubun = "STORE_NOUSE")
//        val policy = incopsPolicyRepository.findByPk(ip_pk)
//        storeNoUse = policy.value2!!
        var storeNoUse : Int? = policyService.searchPolicy("ALL", "STORE_NOUSE", "Y") ?.let { it -> it.value2!! }

        val basic = pcBasicRepository.findBySerial(request.serial)

        if (request.devName.equals("MOBILE-RW")|| request.devName.equals("M")) {
            when (request.allowType) {
                "A", "R" -> request.allowType = "Y"
                "B" -> request.allowType = "N"
            }
            storeNoUse = 0
        }

        //기존 정책 등록 여부 확인
        var actiongb : String = ""
        val psr_pk = PfwStoreRulePk(serial = request.serial, devName = if (request.devName != "M") request.devName else "MOBILE-RW" )

        pfwStoreRuleRepository.findByPkAndGrpGubun(psr_pk, "P")?.let { exits ->
            exits.allowType = request.allowType
            exits.allowLog = request.allowLog
            exits.allowDesc = request.allowDesc
            exits.allowFromdate = DataUtil.lessData(exits.allowFromdate!!,request.allowStartDate) //  if ( exits.allowFromdate!!.toLong() < request.allowStartDate.toLong() ) exits.allowFromdate else request.allowStartDate
            exits.allowTodate = request.allowEndDate
            exits.storeNouse = storeNoUse
            exits.allowDate = DateUtil.nowDateToYYYYMMDD
            exits.regEmpno = request.regEmpno
            exits.deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString()
            exits.lastuseTime = DateUtil.nowDateToYYYYMMDDHHMM

            pfwStoreRuleRepository.save(exits)
            actiongb = "U"
        } ?: run {
            val new = PfwStoreRule(pk = psr_pk,
                empno = (if (request.grpGubun == "P") basic.empno else null).toString(),
                hname = (if (request.grpGubun == "P") basic.hname else null).toString(),
                sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString(),
                indeptnm = (if (request.grpGubun == "P") basic.indeptnm else null).toString(),
                locatenm = (if (request.grpGubun == "P") basic.locatenm else null).toString(),
                allowType = request.allowType,
                allowLog = request.allowLog,
                allowDesc = request.allowDesc,
                allowFromdate = request.allowStartDate,
                allowTodate = request.allowEndDate,
                storeNouse = storeNoUse,
                allowDate = DateUtil.nowDateToYYYYMMDD,
                regEmpno = request.regEmpno,
                deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString(),
                lastuseTime = DateUtil.nowDateToYYYYMMDDHHMM ,
                grpGubun = request.grpGubun,
                removalAllow = null
            )
            pfwStoreRuleRepository.save(new)
            actiongb = "I"
        }

        val log = ExceptionLogData(
            serial = request.serial, grpGubun = request.grpGubun, allowLog = request.allowLog,
            regEmpno = request.regEmpno, allowStartDate = request.allowStartDate, allowEndDate = request.allowEndDate,
            devName = if (request.devName != "M") request.devName else "MOBILE-RW", allowDesc = request.allowDesc,
            poGubun = "EXC_WALL01", actiongb = actiongb, gubun = request.devName,
            poGubundtl = if (request.devName != "M") request.devName else "MOBILE-RW", logVal = request.allowLog, allowVal = request.allowType, remark1=null, remark2=null)
        logService.createLog(log)

//        // 로그 디렉토리 적
//        val log_pk = IncopsPcexceptionLogPK(changeTime = DateUtil.nowDateTimeString,
//            actiongb = actiongb, serial = request.serial, poGubun = "EXC_WALL01", devName = if (request.devName != "M") request.devName else "MOBILE-RW")
//
//        val log = IncopsPcexceptionLog(pk = log_pk,
//            empno = (if (request.grpGubun == "P") basic.empno else null).toString(),
//            hname = (if (request.grpGubun == "P") basic.hname else null).toString(),
//            poGubundtl = if (request.devName != "M") request.devName else "MOBILE-RW",
//            sdeptnm = (if (request.grpGubun == "P") basic.sdeptnm else null).toString(),
//            deptcode = (if (request.grpGubun == "P") basic.deptcode else null).toString(),
//            indeptnm = (if (request.grpGubun == "P") basic.indeptnm else null).toString(),
//            locatenm = (if (request.grpGubun == "P") basic.locatenm else null).toString(),
//            pcGubun = (if (request.grpGubun == "P") basic.pcGubun else null).toString(),
//            grpGubun = request.grpGubun,
//            allowedDate = DateUtil.nowDateTimeString,
//            allowedDesc = request.allowDesc,
//            allowFromdate = request.allowStartDate,
//            allowTodate = request.allowEndDate,
//            ruleNo = null,
//            portName = null,
//            gubun = storeNoUse.toString(),
//            allowVal = request.allowType,
//            logVal = request.allowLog,
//            changer = request.regEmpno,
//            remark1 = null, remark2 = null
//        )
//        //log 저장
//        incopsPcexceptionLogRepository.save(log)

        //MOBILE 장치 처리 추가
        if (request.devName.equals("MOBILE-RW")) {
            val mobileTypes: Array<String> = arrayOf("T", "M", "9")

            for (i in mobileTypes.indices) {
                val pce_pk = PcExceptionPk(serial = request.serial, gubun = mobileTypes[i])
                pcExceptionRepository.findByPk(pce_pk)?.let { exits_pc ->
                    exits_pc.expDate = DateUtil.nowDateTimeString
                    exits_pc.expDesc = request.allowDesc
                    exits_pc.value1 = if (request.allowType == "Y") 0 else 1
                    exits_pc.allowFromdate = DataUtil.lessData(exits_pc.allowFromdate!!, request.allowStartDate) // if ( exits_pc.allowFromdate!!.toLong() < request.allowStartDate.toLong() ) exits_pc.allowFromdate else request.allowStartDate
                    exits_pc.allowTodate = request.allowEndDate
                    exits_pc.regEmpno = request.regEmpno
                    exits_pc.grpGubun = request.grpGubun
                    actiongb = "U"

                    pcExceptionRepository.save(exits_pc)
                } ?: run {
                    val new_pce = PcException(pk = pce_pk,
                        expDate = DateUtil.nowDateTimeString,
                        expDesc = request.allowDesc,
                        value1 = if (request.allowType == "Y") 0 else 1,
                        allowFromdate = request.allowStartDate,
                        allowTodate = request.allowEndDate,
                        regEmpno = request.regEmpno,
                        grpGubun = request.grpGubun
                    )
                    actiongb = "I"
                    pcExceptionRepository.save(new_pce)
                }
                //log 저장
                log.devName = mobileTypes[i]
                log.actiongb = actiongb
                log.poGubundtl = mobileTypes[i]
                log.gubun = mobileTypes[i]
                log.allowVal = (if (request.allowType == "Y") 0 else 1).toString()
                logService.createLog(log)
            }
        }
    }

    fun searchStorageBySerial(serial: String) : List<PfwStoreRule> ? {
        return pfwStoreRuleRepository.findByPkSerial(serial)
    }
}