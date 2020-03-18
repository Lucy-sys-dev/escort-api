package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.HbCompany
import io.ssnc.ac.escort.entity.Insainfo
import io.ssnc.ac.escort.entity.pcUsers
import io.ssnc.ac.escort.entity.pcUsersPK
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InsainfoRepository: CrudRepository<Insainfo, String> {
    fun findByEmpno(empno: String): Insainfo?
}

@Repository
interface PcUsersReposiroty: CrudRepository<pcUsers, String> {
    fun findByPk(pk: pcUsersPK) : pcUsers?
}

@Repository
interface HbCompanyReposiroty: CrudRepository<HbCompany, String> {
    fun findByCompanyCode(companyCode: String): HbCompany
}
