package io.ssnc.ac.escort.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "INCOPS_POLICY", catalog = "dbo")
data class IncopsPolicy (
    @EmbeddedId
    val pk: IncopsPolicyPK? = null,
    @Column(name="value1")
    val value1: String?,
    @Column(name="value2")
    var value2: Int?
): Serializable

@Embeddable
data class IncopsPolicyPK(
    @Column(name = "locatenm")
    val locatenm: String? = null,
    @Column(name = "pc_gubun")
    val pcGbun: String? = null,
    @Column(name = "gubun")
    var gubun: String? = null
) : Serializable

@Entity
@Table(name = "PC_EXITPW", catalog = "dbo")
data class PcExitpw (
    @Id
    @Column(name = "EVENT_TIME")
    val eventTime: String,
    @Column(name="PASSWD")
    val passwd: String,
    @Column(name="PASSWD_KEY")
    var passwdKey: String
): Serializable
