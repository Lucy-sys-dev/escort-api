package io.ssnc.ac.escort.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "PC_BASIC", catalog = "dbo")
data class PcBasic (
    @Id
    @Column(name = "serial") val serial: String,

    @Column(name = "empno") val empno: String?=null,
    @Column(name = "hname") val hname: String?=null,
    @Column(name = "indeptnm") val indeptnm: String?=null,
    @Column(name = "sdeptnm") val sdeptnm: String?=null,
    @Column(name = "locatenm") val locatenm: String?=null,
    @Column(name = "buildnm") val buildnm: String?=null,
    @Column(name = "area") val area: String?=null,
    @Column(name="start_date") val startDate: String?=null,
    @Column(name = "latest_date") val latestDate: String?=null,
    @Column(name = "access_cnt") val accessCnt: Int?=null,
    @Column(name = "error_cnt") val errorCnt: Int?=null,
    @Column(name = "pc_type") val pcType: String?=null,
    @Column(name = "ip_addr") val ipAddr: String?=null,
    @Column(name = "version") val version: String?=null,
    @Column(name = "model") val model: String?=null,
    @Column(name = "pc_gubun") val pcGubun: String?=null,
    @Column(name = "os") val os: String?=null,
    @Column(name = "remarks") val remarks: String?=null,
    @Column(name = "sebu_area") val sebuArea: String?=null,
    @Column(name = "prop_no") val propNo: String?=null,
    @Column(name = "computer_name") val computerName: String?=null,
    @Column(name = "cpuid") val cpuid: String?=null,
    @Column(name = "mac") val mac: String?=null,
    @Column(name = "ownership") val ownership: String?=null,
    @Column(name = "prop_gubun") val propGubun: Int?=null,
    @Column(name = "real_ipaddr") val realIpaddr: String?=null,
    @Column(name = "pc_used") val pcUsed: String?=null,
    @Column(name = "temp_save") val tempSave: Int?=null,
    @Column(name = "residence") val residence: Int?=null,
    @Column(name = "vup_time") val vupTime: String?=null,
    @Column(name = "deptcode") val deptcode: String?=null,
    @Column(name = "madecode") val madecode: String?=null,
    @Column(name = "pcid") val pcid: String?=null,
    @Column(name = "pcid4nasca") val pcid4nasca: String?=null,
    @Column(name = "buildver") val buildver: String?=null,
    @Column(name = "empno_first_reg") val empnoFirstReg: String?=null,
    @Column(name = "hname_first_reg") val hnameFirstReg: String?=null
): Serializable

@Entity
@Table(name = "GROUPLIST", catalog = "dbo")
data class Grouplist (
    @EmbeddedId
    val pk: GrouplistPk,
    @Column(name = "CODEVALUE") var codevalue: String? = null,
    @Column(name = "VALUEGUBUN") var valuegubun: String? = null
) : Serializable {
}

@Embeddable
data class GrouplistPk(
    @Column(name = "GROUPCODE") val groupcode: String,
    @Column(name = "GROUPSEQ") val groupseq: Int
) : Serializable

@Entity
@Table(name = "PC_DEPTCODE", catalog = "dbo")
data class PcDeptcode(
    @Id
    @Column(name = "DEPTCODE") val deptcode: String,
    @Column(name = "DEPTNAME") val deptname: String,
    @Column(name = "DEPTENGNAME") val deptengname: String? = null,
    @Column(name = "HIGHDEPTCODE") val highdeptcode: String,
    @Column(name = "DEPTH") val depth: Int? = null,
    @Column(name = "FNAMEKOR") val fnamekor: String? = null,
    @Column(name = "FNAMEENG") val fnameeng: String? = null,
    @Column(name = "NORDER") val norder: Int? = null,
    @Column(name = "NLEVEL") val nlevel: Int? = null,
    @Column(name = "MADECODE") val madecode: String? = null,
    @Column(name = "ORIGIN") val origin: String? = null
)