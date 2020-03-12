package io.ssnc.ac.escort.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "SSA_CALLSTACK_VERINFO", catalog = "dbo")
data class SsaClaastackVerinfo (
    @Id
    @Column(name = "REG_DATE")
    @Temporal( TemporalType.TIMESTAMP )
    var regDate: Date,

    @Column(name = "ssaver")
    var ssaver : String,

    @Column(name = "cs_plpcsinfo")
    var csPlpcsinfo: String?,

    @Column(name = "cs_sitecsinfo")
    var csSitecsinfo: String?,

    @Column(name = "ed_plp")
    var edPlp: String?,

    @Column(name = "ed_site")
    var edSite: String?

) : Serializable