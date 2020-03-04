package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.PfwStoreRule
import io.ssnc.ac.accessControl.entity.PfwStoreRulePk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PfwStoreRuleRepository : JpaRepository<PfwStoreRule, String> {
    fun findByPk(pk: PfwStoreRulePk) : PfwStoreRule?
    fun findByPkAndGrpGubun(pk: PfwStoreRulePk, grpGubun: String) : PfwStoreRule?
}