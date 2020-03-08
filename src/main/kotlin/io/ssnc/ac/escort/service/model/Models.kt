package io.ssnc.ac.escort.service.model

data class PcIcatExpListTypes(
    var gubun: String,
    var value1: String?
)

data class ExceptionLogData (
    val serial: String,
    val grpGubun: String,
    var allowLog: String,
    val regEmpno: String,
    val allowStartDate: String,
    val allowEndDate: String,
    var devName: String,
    val allowDesc: String,
    val poGubun: String,
    var poGubundtl: String,
    var actiongb: String,
    val logVal: String,
    var gubun: String,
    var remark1: String?,
    var remark2: String?,
    var allowVal: String?
)