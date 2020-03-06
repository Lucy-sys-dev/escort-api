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

@Entity
@Table(name = "PFW_MEDIA_RULE", catalog = "dbo")
data class PfwMediaRule (
    @EmbeddedId
    val pk: PfwMediaRulePk? = null,
    @Column(name = "PGM_NAME") var pgmName: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "REG_DATE") var regDate: String? = null,
    @Column(name = "REMARK") var remark: String? = null,
    @Column(name = "ALLOW_GUBUN") var allowGubun: Int? = null,
    @Column(name = "TIME_INDEX") var timeIndex: Int? = null,
    @Column(name = "USE_GUBUN") var useGubun: String? = null
) : Serializable

@Embeddable
data class PfwMediaRulePk(
    @Column(name = "GUBUN") val gubun: String? = null,
    @Column(name = "PROC_NAME") val procName: String? = null
) : Serializable

@Entity
@Table(name = "PFW_MEDIA_EXCEPTION", catalog = "dbo")
data class PfwMediaException (
    @EmbeddedId
    val pk: PfwMediaExceptionPk? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "ALLOW_DESC") var allowDesc: String? = null,
    @Column(name = "ALLOW_GUBUN") var allowGubun: Int? = null,
    @Column(name = "REG_DATE") var regDate: String? = null,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null
) : Serializable

@Embeddable
data class PfwMediaExceptionPk(
    @Column(name = "GUBUN") val gubun: String? = null,
    @Column(name = "SERIAL") val serial: String? = null,
    @Column(name = "PROC_NAME") val procName: String? = null
) : Serializable

@Entity
@Table(name = "PC_ICAT_APP", catalog = "dbo")
data class PcIcatApp (
    @EmbeddedId
    val pk: PcIcatAppPk? = null,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "RUNNING") var running: Int? = null
) : Serializable

@Embeddable
data class PcIcatAppPk(
    @Column(name = "APPEXE") val appexe: String,
    @Column(name = "APPNAME") val appname: String
) : Serializable

@Entity
@Table(name = "PC_ICAT_CLSID", catalog = "dbo")
data class PcIcatClsid (
    @EmbeddedId
    val pk: PcIcatClsidPk? = null,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "RUNNING") var running: Int? = null
) : Serializable

@Embeddable
data class PcIcatClsidPk(
    @Column(name = "SITENAME") val sitename: String,
    @Column(name = "CLSID") val clsid: String
) : Serializable

@Entity
@Table(name = "PC_ICAT_EXP_LIST", catalog = "dbo")
data class PcIcatExpList (
    @EmbeddedId
    val pk: PcIcatExpListPk? = null,
    @Column(name = "VALUE2") var value2: String? = null,
    @Column(name = "STARTTIME") var starttime: String? = null,
    @Column(name = "ENDTIME") var endtime: String? = null,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "ALLOW_DESC") var allowDesc: String? = null

) : Serializable

@Embeddable
data class PcIcatExpListPk(
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "GRP_GUBUN") val grpGubun: String,
    @Column(name = "GUBUN") val gubun: String,
    @Column(name = "VALUE1") val value1: String
) : Serializable