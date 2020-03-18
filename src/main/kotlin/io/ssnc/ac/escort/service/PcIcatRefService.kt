package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.PcPgmList
import io.ssnc.ac.escort.repository.PcPgmListRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PcIcatRefService {
    companion object : KLogging()

    @Autowired
    lateinit var pcPgmListRepository: PcPgmListRepository

    fun searchPcPgmList() {

    }

    fun searchPcPgmListbyFileName(filename: String) : PcPgmList? {
        return pcPgmListRepository.findTopByFileName(filename)
    }
}
