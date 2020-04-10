package io.ssnc.ac.escort.entity

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
) : Serializable {
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "REGEMPNO",referencedColumnName="EMPNO", insertable = false, updatable = false)
    var regEmpno: PcBasic? = null
}

@Embeddable
data class PcIcatAppPk(
    @Column(name = "APPEXE") val appexe: String,
    @Column(name = "APPNAME") val appname: String
) : Serializable

@Entity
@Table(name = "PC_ICAT_EX_IP", catalog = "dbo")
data class PcIcatExIp (
    @EmbeddedId
    val pk: PcIcatExIpPk,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "RUNNING") var running: Int? = null,
    @Column(name = "IP_END") var ipEnd: String? = null
) : Serializable {
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "REGEMPNO",referencedColumnName="EMPNO", insertable = false, updatable = false)
    var regEmpno: PcBasic? = null
}

@Embeddable
data class PcIcatExIpPk(
    @Column(name = "SITENAME") val sitename: String,
    @Column(name = "IP") val ip: String
) : Serializable

@Entity
@Table(name = "PC_ICAT_EX_HOST", catalog = "dbo")
data class PcIcatExHost (
    @EmbeddedId
    val pk: PcIcatExHostPk,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "RUNNING") var running: Int? = null
) : Serializable {
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "REGEMPNO",referencedColumnName="EMPNO", insertable = false, updatable = false)
    var regEmpno: PcBasic? = null
}

@Embeddable
data class PcIcatExHostPk(
    @Column(name = "SITENAME") val sitename: String,
    @Column(name = "HOST") val host: String
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
    val pk: PcIcatExpListPk,
    @Column(name = "VALUE2") var value2: String? = null,
    @Column(name = "STARTTIME") var starttime: String? = null,
    @Column(name = "ENDTIME") var endtime: String? = null,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "ALLOW_DESC") var allowDesc: String? = null
) : Serializable {
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "SERIAL",referencedColumnName="SERIAL", insertable = false, updatable = false)
    var pcIcatExp: PcIcatException? = null
}

@Embeddable
data class PcIcatExpListPk(
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "GRP_GUBUN") val grpGubun: String,
    @Column(name = "GUBUN") val gubun: String,
    @Column(name = "VALUE1") val value1: String
) : Serializable

@Entity
@Table(name = "PC_ICAT_EXCEPTION", catalog = "dbo")
data class PcIcatException (
    @Id
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "EMPNO") var empno: String? = null,
    @Column(name = "HNAME") var hname: String? = null,
    @Column(name = "SDEPTNM") var sdeptnm: String? = null,
    @Column(name = "GUBUN") var gubun: String? = null,
    @Column(name = "REGEMPNO") var regempno: String? = null,
    @Column(name = "REGDATE") var regdate: String? = null,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "ALLOW_DESC") var allowDesc: String? = null,
    @Column(name = "DEPTCODE") var deptcode: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null
) : Serializable

@Entity
@Table(name = "PC_EXCEPTION_MULTI", catalog = "dbo")
data class PcExceptionMulti (
    @EmbeddedId
    val pk: PcExceptionMultiPk,
    @Column(name = "EXP_DATE") var expDate: String? = null,
    @Column(name = "EXP_DESC") var expDesc: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null
) : Serializable {
}

@Embeddable
data class PcExceptionMultiPk(
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "GUBUN") val gubun: String,
    @Column(name = "VALUE1") val value1: Int,
    @Column(name = "ALLOW_FROMDATE") val allowFromdate: String,
    @Column(name = "ALLOW_TODATE") val allowTodate: String
) : Serializable

@Entity
@Table(name = "PC_EXCEPTION_MULTI_LOG", catalog = "dbo")
data class PcExceptionMultiLog (
    @EmbeddedId
    val pk: PcExceptionMultiLogPk,
    @Column(name = "OLD_EXP_DATE") var oldExpDate: String? = null,
    @Column(name = "OLD_EXP_DESC") var oldExpDesc: String? = null,
    @Column(name = "OLD_VALUE1") var oldValue1: Int? = null,
    @Column(name = "OLD_ALLOW_FROMDATE") var oldAllowFromdate: String? = null,
    @Column(name = "OLD_ALLOW_TODATE") var oldAllowTodate: String? = null,
    @Column(name = "OLD_REG_EMPNO") var oldRegEmpno: String? = null,
    @Column(name = "OLD_GRP_GUBUN") var oldGrpGubun: String? = null,
    @Column(name = "NEW_EXP_DATE") var newExpDate: String? = null,
    @Column(name = "NEW_EXP_DESC") var newExpDesc: String? = null,
    @Column(name = "NEW_VALUE1") var newValue1: Int? = null,
    @Column(name = "NEW_ALLOW_FROMDATE") var newAllowFromdate: String? = null,
    @Column(name = "NEW_ALLOW_TODATE") var newAllowTodate: String? = null,
    @Column(name = "NEW_REG_EMPNO") var newRegEmpno: String? = null,
    @Column(name = "NEW_GRP_GUBUN") var newGrpGubun: String? = null

) : Serializable {
}

@Embeddable
data class PcExceptionMultiLogPk(
    @Column(name = "LOG_TIME") val logTime: String,
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "GUBUN") val gubun: String
) : Serializable

@Entity
@Table(name = "PC_REG_USBDEVICE", catalog = "dbo")
data class PcRegUsbdevice (
    @Id
    @Column(name = "USBSERIAL") var usbserial: String,
    @Column(name = "VPID") var vpid: String,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "BIGO") var bigo: String? = null,
    @Column(name = "REG_DATE") var regDate: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "RUNNING") var running: Int? = null
) : Serializable {
}

@Entity
@Table(name = "PC_REG_USBEXCEPTION", catalog = "dbo")
data class PcRegUsbexception (
    @EmbeddedId
    val pk: PcRegUsbexceptionPk,
    @Column(name = "ALLOW_TYPE") var allowType: String,
    @Column(name = "ALLOW_LOG") var allowLog: String,
    @Column(name = "BIGO") var bigo: String? = null,
    @Column(name = "ALLOW_FROMDATE") var allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") var allowTodate: String? = null,
    @Column(name = "GRP_GUBUN") var grpGubun: String? = null,
    @Column(name = "REG_DATE") var regDate: String? = null,
    @Column(name = "REG_EMPNO") var regEmpno: String? = null,
    @Column(name = "STORE_NOUSE") var storeNouse: Int? = null,
    @Column(name = "LASTUSE_TIME") var lastuseTime: String? = null
) : Serializable {
}

@Embeddable
data class PcRegUsbexceptionPk(
    @Column(name = "SERIAL") val serial: String,
    @Column(name = "USBSERIAL") val usbserial: String
) : Serializable