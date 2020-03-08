package io.ssnc.ac.escort.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PC_ICAT_LOG", catalog = "dbo")
data class Log (
    @EmbeddedId
    val logPk: LogPK? = null,
    @Column(name = "ATT_APPNAME") val attAppname: String? = null,
    @Column(name = "ATT_SIZE") val attSize: Long? = null,
    @Column(name = "IP") val ip: String? = null,
    @Column(name = "EMPNO") val empno: String? = null,
    @Column(name = "HNAME") val hname: String? = null,
    @Column(name = "SDEPTNM") val sdeptnm: String? = null,
    @Column(name = "DEPTCODE") val deptcode: String? = null,
    @Column(name = "LOCATENM") val locatenm: String? = null,
    @Column(name = "MADECODE") val madecode: String? = null
) : Serializable

@Embeddable
data class LogPK (
    @Column(name = "EVENT_TIME") val eventTime: String? = null,
    @Column(name = "SERIAL") val serial: String? = null,
    @Column(name="TYPE") val type: String? = null,
    @Column(name = "ATT_FILENAME") val attFilename: String? = null
) : Serializable


@Entity
@Table(name = "INCOPS_PCEXCEPTION_LOG", catalog = "dbo")
data class IncopsPcexceptionLog (
    @EmbeddedId
    val pk: IncopsPcexceptionLogPK? = null,
    @Column(name = "EMPNO") val empno: String? = null,
    @Column(name = "HNAME") val hname: String? = null,
    @Column(name = "PO_GUBUNDTL") var poGubundtl: String? = null,
    @Column(name = "SDEPTNM") val sdeptnm: String? = null,
    @Column(name = "DEPTCODE") val deptcode: String? = null,
    @Column(name = "INDEPTNM") val indeptnm: String? = null,
    @Column(name = "LOCATENM") val locatenm: String? = null,
    @Column(name = "PC_GUBUN") val pcGubun: String? = null,
    @Column(name = "GRP_GUBUN") val grpGubun: String? = null,
    @Column(name = "ALLOWED_DATE") val allowedDate: String? = null,
    @Column(name = "ALLOWED_DESC") val allowedDesc: String? = null,
    @Column(name = "ALLOW_FROMDATE") val allowFromdate: String? = null,
    @Column(name = "ALLOW_TODATE") val allowTodate: String? = null,
    @Column(name = "RULE_NO") val ruleNo: String? = null,
    @Column(name = "PORT_NAME") val portName: String? = null,
    @Column(name = "GUBUN") var gubun: String? = null,
    @Column(name = "ALLOW_VAL") var allowVal: String? = null,
    @Column(name = "LOG_VAL") val logVal: String? = null,
    @Column(name = "CHANGER") val changer: String? = null,
    @Column(name = "remark1") val remark1: String? = null,
    @Column(name = "remark2") val remark2: String? = null
) : Serializable

@Embeddable
data class IncopsPcexceptionLogPK (
    @Column(name = "CHANGE_TIME") val changeTime: String? = null,
    @Column(name = "ACTIONGB") var actiongb: String? = null,
    @Column(name="SERIAL") val serial: String? = null,
    @Column(name = "PO_GUBUN") val poGubun: String? = null,
    @Column(name = "DEV_NAME") var devName: String? = null
) : Serializable
