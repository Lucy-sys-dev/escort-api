package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LogRepository : JpaRepository<Log, String> {
}

@Repository
interface IncopsPcexceptionLogRepository: JpaRepository<IncopsPcexceptionLog, String> {

}

@Repository
interface IncopsPolicyRepository : JpaRepository<IncopsPolicy, String> {
    fun findByPk(pk: IncopsPolicyPK) : IncopsPolicy
}