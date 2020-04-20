package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.Grouplist
import io.ssnc.ac.escort.entity.GrouplistPk
import io.ssnc.ac.escort.entity.PcBasic
import io.ssnc.ac.escort.entity.PcDeptcode
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PcBasicRepository: CrudRepository<PcBasic, String> {
    fun findBySerial(serial: String): PcBasic
    fun findByEmpno(empno: String) : List<PcBasic>?
}

@Repository
interface GrouplistRepository: CrudRepository<Grouplist, String> {
    fun findByPk(pk: GrouplistPk) : Grouplist
    fun findByCodevalueAndValuegubunIn(codevalue: String, valuegubuns: List<String>): List<Grouplist>?
    fun findByCodevalue(codevalue: String): List<Grouplist>?
}

@Repository
interface PcDeptcodeRepository: CrudRepository<PcDeptcode, String> {
    fun findByDeptcode(deptcode: String): PcDeptcode?

}
