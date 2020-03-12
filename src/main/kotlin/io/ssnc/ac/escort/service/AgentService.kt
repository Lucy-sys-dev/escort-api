package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.Log
import io.ssnc.ac.escort.entity.LogPK
import io.ssnc.ac.escort.entity.SsaClaastackVerinfo
import io.ssnc.ac.escort.entity.request.LogRequest
import io.ssnc.ac.escort.repository.LogRepository
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.SsaClassstackVerinfoRepository
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.io.File
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
}