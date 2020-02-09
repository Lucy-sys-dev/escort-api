package io.ssnc.ac.accessControl.entity.request

data class LogRequest (
    val serial: String,
    val type: String,
    val att_filename: String
)