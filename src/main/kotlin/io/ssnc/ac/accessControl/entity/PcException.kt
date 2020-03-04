package io.ssnc.ac.accessControl.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PC_EXCEPTION", catalog = "dbo")
data class PcException (
    @EmbeddedId
    val pk: PcExceptionPk? = null,
    @Column(name = "EXP_DATE") var expDate: String? = null,
    @Column(name = "EXP_DESC") var expDesc: String? = null,
    @Column(name = "VALUE1") var value1: Int? = null,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null

) : Serializable

@Embeddable
data class PcExceptionPk(
    @Column(name = "SERIAL") val serial: String? = null,
    @Column(name = "GUBUN") val gubun: String? = null
) : Serializable