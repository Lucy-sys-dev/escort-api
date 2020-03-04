package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.Insainfo
import io.ssnc.ac.accessControl.entity.pcUsers
import io.ssnc.ac.accessControl.entity.pcUsersPK
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
