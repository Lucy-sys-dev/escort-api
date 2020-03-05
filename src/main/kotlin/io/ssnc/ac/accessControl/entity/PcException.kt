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


@Entity
@Table(name = "PC_PGM_EXCEPTION", catalog = "dbo")
data class PcPgmException (
    @EmbeddedId
    val pk: PcPgmExceptionPk? = null,
    @Column(name = "EXP_DATE") var expDate: String? = null,
    @Column(name = "EXP_DESC") var expDesc: String? = null,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null
) : Serializable

@Embeddable
data class PcPgmExceptionPk(
    @Column(name = "SEQ") val seq: Int?,
    @Column(name = "SERIAL") val serial: String?
) : Serializable

@Entity
@Table(name = "PC_PGM_LIST", catalog = "dbo")
data class PcPgmList (
    @Id
    @Column(name = "SEQ") var seq: Int,
    @Column(name = "PGM_NAME") var pgmName: String? = null,
    @Column(name = "FILE_NAME") var fileName: String? = null,
    @Column(name = "GUBUN") var gubun: Int? = null,
    @Column(name = "COMP_GUBUN") var compGubun: Int? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "REG_DATE") var regDate: String? = null,
    @Column(name = "REMARK") var remark: String? = null,
    @Column(name = "TIME_INDEX") var timeIndex: Int? = null,
    @Column(name = "USE_GUBUN") var useGubun: String? = null


) : Serializable