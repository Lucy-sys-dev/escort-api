package io.ssnc.ac.escort.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "INSAINFO", catalog = "dbo")
data class Insainfo (
    @Id
    @Column(name = "EMPNO") var empno: String,
    @Column(name = "HNAME") var hname: String? = null,
    @Column(name = "RESNO") var resno: String? = null,
    @Column(name = "SDEPTNM") var sdeptnm: String? = null,
    @Column(name = "LOCATE1") var locate1: String? = null,
    @Column(name = "LOCATE2") var locate2: String? = null,
    @Column(name = "STATUS") var status: String? = null,
    @Column(name = "ENTERDATE") var enterdate: String? = null,
    @Column(name = "OUTDATE") var outdate: String? = null,
    @Column(name = "EMPTYPE") var emptype: String? = null,
    @Column(name = "INDEPTNM") var indeptnm: String? = null,
    @Column(name = "INDEPT") var indept: String? = null,
    @Column(name = "JIKGUBNM") var jikgubnm: String? = null,
    @Column(name = "JIKGAN") var jikgan: String? = null,
    @Column(name = "LOCATECODE") var locatecode: String? = null,
    @Column(name = "PASSWORD") var password: String? = null,
    @Column(name = "USER_KEY") var userKey: String? = null,
    @Column(name = "USE_STARTDATE") var useStartdate: String? = null,
    @Column(name = "USE_ENDDATE") var useEnddate: String? = null,
    @Column(name = "USE_END_USER") var useEndUser: Int? = null,
    @Column(name = "USE_END_IP") var useEndIp: Int? = null,
    @Column(name = "USE_END_PC") var useEndPc: Int? = null,
    @Column(name = "ONBUSINESS") var onbusiness: Int? = null,
    @Column(name = "HHP_NO") var hhpNo: String? = null,
    @Column(name = "ORIGIN") var origin: String? = null,
    @Column(name = "UID") var uid: String? = null,
    @Column(name = "OLD_EMPNO") var oldEmpno: String? = null,
    @Column(name = "SDEPTCODE") var sdeptcode: String? = null,
    @Column(name = "COMPANY") var company: String? = null,
    @Column(name = "DEPTCODE") var deptcode: String? = null,
    @Column(name = "PUBLIC_REG") var publicReg: String? = null,
    @Column(name = "EMPKIND") var empkind: String? = null,
    @Column(name = "GRADELEVEL") var gradelevel: Int? = null,
    @Column(name = "EJIKGUBNM") var ejikgubnm: String? = null,
    @Column(name = "LANGUAGE") var language: String? = null,
    @Column(name = "MADECODE") var madecode: String? = null,
    @Column(name = "COMPANYNAME") var companyname: String? = null,
    @Column(name = "MAIL_ADDR") var mailAddr: String? = null
) : Serializable

@Entity
@Table(name = "PC_USERS", catalog = "dbo")
data class pcUsers (
    @EmbeddedId
    val pk: pcUsersPK? = null,
    @Column(name = "HNAME") val hname: String? = null,
    @Column(name = "PASSWORD") var password: String? = null,
    @Column(name = "REG_DT") val regDt: Date? = null,
    @Column(name = "CHANGE_PWD_DT") var changePwdDt: Date? = null,
    @Column(name = "DEPT_CODE") var deptCode: String? = null,
    @Column(name = "DEPT_NAME") var deptName: String? = null,
    @Column(name = "RESNO") var resno: String? = null,
    @Column(name = "EMP_TYPE") var empType: String? = null,
    @Column(name = "STATUS") var status: String? = null,
    @Column(name = "COMPANY_NAME") var companyName: String? = null,
    @Column(name = "PASSWORD_ENCRYPT") var passwordEncrypt: String? = null

) : Serializable

@Embeddable
data class pcUsersPK(
    @Column(name = "EMPNO") val empno: String? = null,
    @Column(name = "AFFILIATE") val affiliate: String? = null
) : Serializable

@Entity
@Table(name = "HB_COMPANY", catalog = "dbo")
data class HbCompany (
    @Id
    @Column(name = "COMPANY_CODE") val companyCode: String,
    @Column(name = "COMPANY_NAME") val companyName: String? = null,
    @Column(name = "COMPANY_ENGNAME") var companyEngname: String? = null
) : Serializable