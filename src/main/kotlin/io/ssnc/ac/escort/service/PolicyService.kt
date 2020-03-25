package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.IncopsPolicy
import io.ssnc.ac.escort.entity.IncopsPolicyPK
import io.ssnc.ac.escort.repository.IncopsPolicyRepository
import io.ssnc.ac.escort.repository.PcBasicRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PolicyService {
    companion object : KLogging()

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var incopsPolicyRepository: IncopsPolicyRepository

    // 정책 조회
    fun searchPolicy(serial: String, gubun: String, companyPolicy: String) : IncopsPolicy? {
        var pk: IncopsPolicyPK = IncopsPolicyPK()
        if (serial.equals("ALL")) {
            pk = IncopsPolicyPK(locatenm = "ALL", pcGbun = "Z", gubun = gubun)
        } else {
            val basic = pcBasicRepository.findBySerial(serial)
            when (companyPolicy) {
                "A" -> pk = IncopsPolicyPK(locatenm = "ALL", pcGbun = basic.pcGubun, gubun = gubun)
                "Y" -> pk = IncopsPolicyPK(locatenm = basic.locatenm, pcGbun = "Z", gubun = gubun)
                else -> pk = IncopsPolicyPK(locatenm = basic.locatenm, pcGbun = basic.pcGubun, gubun = gubun)
            }
        }
        return incopsPolicyRepository.findByPk(pk)
    }

    fun checkDefaultPolicy(serial: String, gubun: String) : String {
        searchPolicy(serial, gubun, "N")?.let { it ->
            when (it.value1) {
                "A", "Y" -> return "ON"
                else -> return "OFF"
            }
        } ?: run {
            searchPolicy(serial, gubun, "Y")?.let { it ->
                when (it.value1) {
                    "A", "Y" -> return "ON"
                    else -> return "OFF"
                }
            } ?: run {
                searchPolicy(serial, gubun, "A")?.let { it ->
                    when (it.value1) {
                        "A", "Y" -> return "ON"
                        else -> return "OFF"
                    }
                } ?: run {
                    searchPolicy("ALL", gubun, "A")?.let { it ->
                        when (it.value1) {
                            "A", "Y" -> return "ON"
                            else -> return "OFF"
                        }
                    }
                }
            }
        }
        return "OFF"
    }
}