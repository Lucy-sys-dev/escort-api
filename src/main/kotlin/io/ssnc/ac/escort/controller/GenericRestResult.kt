package io.ssnc.ac.escort.controller

import com.fasterxml.jackson.annotation.JsonInclude

data class GenericRestResult(
    /**
     * result code. normally 0 = ok/success, others = error
     */
    val result: Int,

    /**
     * any necessary message for user
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val msg: String? = null,

    /**
     * any data
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: Any? = null,

    /**
     * extra data
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val extra: Any? = null
)