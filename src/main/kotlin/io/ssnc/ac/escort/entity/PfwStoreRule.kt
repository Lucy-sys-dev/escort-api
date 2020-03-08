package io.ssnc.ac.escort.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PFW_STORE_RULE", catalog = "dbo")
data class PfwStoreRule (
    @EmbeddedId
    val pk: PfwStoreRulePk? = null,
    @Column(name="empno") val empno: String?,
    @Column(name="hname") val hname: String?,
    @Column(name="sdeptnm") val sdeptnm: String?,
    @Column(name="indeptnm") val indeptnm: String?,
    @Column(name="locatenm") val locatenm: String?,
    @Column(name="allow_type") var allowType: String?,
    @Column(name="allow_log") var allowLog: String?,
    @Column(name="allow_date") var allowDate: String?,
    @Column(name="allow_desc") var allowDesc: String?,
    @Column(name="allow_todate") var allowTodate: String?,
    @Column(name="allow_fromdate") var allowFromdate: String?,
    @Column(name="store_nouse") var storeNouse: Int?,
    @Column(name="lastuse_time") var lastuseTime: String?,
    @Column(name="reg_empno") var regEmpno: String?,
    @Column(name="removal_allow") val removalAllow: String?,
    @Column(name="deptcode") var deptcode: String?,
    @Column(name="grp_gubun") val grpGubun: String?
) : Serializable

@Embeddable
data class PfwStoreRulePk(
    @Column(name = "serial") val serial: String? = null,
    @Column(name = "dev_name") val devName: String? = null
) : Serializable
