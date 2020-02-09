package io.ssnc.ac.accessControl.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PC_ICAT_LOG", schema = "incops5")
data class Log (
    @EmbeddedId
    val logPk: LogPK? = null,
    @Column(name = "ATT_APPNAME")
    val attAppname: String? = null,
    @Column(name = "ATT_SIZE")
    val attSize: Long? = null,
    @Column(name = "IP")
    val ip: String? = null,
    @Column(name = "EMPNO")
    val empno: String? = null,
    @Column(name = "HNAME")
    val hname: String? = null,
    @Column(name = "SDEPTNM")
    val sdeptnm: String? = null,
    @Column(name = "DEPTCODE")
    val deptcode: String? = null,
    @Column(name = "LOCATENM")
    val locatenm: String? = null,
    @Column(name = "MADECODE")
    val madecode: String? = null
) : Serializable

@Embeddable
data class LogPK (
    @Column(name = "EVENT_TIME")
    val eventTime: String? = null,
    @Column(name = "SERIAL")
    val serial: String? = null,
    @Column(name="TYPE")
    val type: String? = null,
    @Column(name = "ATT_FILENAME")
    val attFilename: String? = null
) : Serializable

