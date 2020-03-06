package io.ssnc.ac.accessControl.entity.request

data class AccessControlRequest (
    val serial: String,
    val regEmpno: String,
    val grpGubun: String = "P",
    var allowLog: String,
    val storages : List<Storage>?,
    val programs : List<Program>?,
    val devices : List<Storage>?,
    val files: List<file>?
)

data class Storage (
    val allowStartDate: String,
    val allowEndDate: String,
    val devName: String,
    var allowType: String,
    val allowDesc: String,
    val pgmLists: List<String>?
)

data class StoreRule (
    val serial: String,
    val grpGubun: String,
    var allowLog: String,
    val regEmpno: String,
    val allowStartDate: String,
    val allowEndDate: String,
    var devName: String,
    var allowType: String,
    val allowDesc: String,
    val pgmLists: List<String>?
)

data class Program (
    val allowStartDate: String,
    val allowEndDate: String,
    val devName: String,
    var allowType: String,  //0(허가), 1(통제)
    val allowDesc: String
    //제외(fix) "allowPgmList": "netpia.exe|free.exe", //예외프로그램 목록 '|'를 사용하여 다수 프로그램 등록
)

data class devices (
    val allowStartDate: String,
    val allowEndDate: String,
    val devName: String,
    var allowType: String,  //0(허가), 1(통제), 2(사외만제외), 3(사내&사외모두예외)
    val allowDesc: String
)

data class file(
    val allowStartDate: String,
    val allowEndDate: String,
    var allowType: String,  //0(허가), 1(통제), 2(사외만제외), 3(사내&사외모두예외)
    val allowDesc: String,
    val excepts: List<except>?
)

data class except(
    val subGubun: String,
    val subValue1: String,
    val subValue2: String?
)