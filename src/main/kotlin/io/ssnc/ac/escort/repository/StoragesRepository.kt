package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.PfwStoreRule
import io.ssnc.ac.escort.entity.PfwStoreRulePk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PfwStoreRuleRepository : JpaRepository<PfwStoreRule, String> {
    fun findByPk(pk: PfwStoreRulePk) : PfwStoreRule?
    fun findByPkAndGrpGubun(pk: PfwStoreRulePk, grpGubun: String) : PfwStoreRule?
    fun findByPkSerial(serial: String): List<PfwStoreRule>?
}