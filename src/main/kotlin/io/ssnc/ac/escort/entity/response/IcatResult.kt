package io.ssnc.ac.escort.entity.response

import io.ssnc.ac.escort.entity.IcatCtrlBase
import io.ssnc.ac.escort.entity.IcatException

data class IcatResult (
    val serial: String,
    val empno: String,
    val hname: String,
    val locatenm: String,
    val pc_gubun: String,
    val ctrl_onoff: String,
    val logging_onoff: String,
    var icat_base: List<IcatCtrlBase>?,
    var icat_exception: List<IcatException>?
)
