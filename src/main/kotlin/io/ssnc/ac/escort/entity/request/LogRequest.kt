package io.ssnc.ac.escort.entity.request

data class LogRequest (
    val serial: String,
    val type: String,
    val att_filename: String,
    val att_appname: String?,
    val att_size: Long?,
    val ip: String?
)