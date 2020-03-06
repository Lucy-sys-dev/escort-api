package io.ssnc.ac.accessControl.entity.response

data class LoginResponse (
    var affiliate: String,
    var id: String,
    var name: String,
    var dept_name: String,
    var dept_code: String,
    var emp_type: String?,
    var status: String?,
    var company_name: String?
)

data class StatusResponse (
    var affiliate: String,
    var id: String,
    var status: String
)