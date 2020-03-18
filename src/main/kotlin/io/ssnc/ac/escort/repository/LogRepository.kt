package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LogRepository : JpaRepository<Log, String> {
}

@Repository
interface IncopsPcexceptionLogRepository: JpaRepository<IncopsPcexceptionLog, String> {

}

@Repository
interface IncopsPolicyRepository : JpaRepository<IncopsPolicy, String> {
    fun findByPk(pk: IncopsPolicyPK) : IncopsPolicy?
}