package io.ssnc.ac.escort.entity

import java.io.Serializable
import javax.persistence.*


@Entity
@NamedStoredProcedureQuery(
//    NamedStoredProcedureQuery(
        name = "getIcatCtrlDefault",
        procedureName = "dbo.SSA_USP_ICAT_CTRL_DEFAULT",
        resultClasses = [IcatCtrlDefault::class],
        parameters = [
            StoredProcedureParameter(name = "serial", type = String::class, mode = ParameterMode.IN)
        ]
//    )
)
data class IcatCtrlDefault (
    @Id
    var serial: String,
    var empno: String,
    var hname: String,
    var locatenm: String,
    var pcGubun: String,
    var ctrlOnoff: String,
    var loggingOnoff: String
) : Serializable

@Entity
@NamedStoredProcedureQuery(
//    NamedStoredProcedureQuery(
    name = "getIcatCtrlBasic",
    procedureName = "dbo.SSA_USP_ICAT_EXCE_BASIC",
    resultClasses = [IcatCtrlBase::class]
//    )
)
data class IcatCtrlBase (
    @Id
    var ctrlGubun: String,
    var expType: String,
    var expVal1: String,
    var expVal2: String
) : Serializable
//@SqlResultSetMappings(
//    SqlResultSetMapping(name = "icatCtrlDefault",
//        classes = [ConstructorResult(targetClass = IcatCtrlDefault::class,
//            columns = [
//                ColumnResult(name = "serial", type = String::class),
//                ColumnResult(name = "empno", type = String::class),
//                ColumnResult(name = "hname", type = String::class),
//                ColumnResult(name = "locatenm", type = String::class),
//                ColumnResult(name = "pc_gubun", type = String::class),
//                ColumnResult(name = "ctrl_onoff", type = String::class),
//                ColumnResult(name = "logging_onoff", type = String::class)
//            ])])
//)

@Entity
@NamedStoredProcedureQuery(
//    NamedStoredProcedureQuery(
    name = "getIcatException",
    procedureName = "dbo.SSA_USP_ICAT_EXCEPTION",
    resultClasses = [IcatException::class],
    parameters = [
        StoredProcedureParameter(name = "serial", type = String::class, mode = ParameterMode.IN)
    ]
//    )
)
data class IcatException (
    @Id
    var serial: String,
    var ctrlGubun: String,
    var expType: String,
    var expVal1: String?,
    var expVal2: String?,
    var allowFromdate: String,
    var allowTodate: String
) : Serializable




