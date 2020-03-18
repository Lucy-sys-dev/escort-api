package io.ssnc.ac.escort.repository

import io.ssnc.ac.escort.entity.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PCIcatDefaultRepository: CrudRepository<IcatCtrlDefault, String> {

    @Procedure(name="getIcatCtrlDefault")
    fun getIcatCtrlDefault(@Param("serial") serial: String) //: IcatCtrlDefault?
}

@Repository
interface PCIcatBasicRepository: CrudRepository<IcatCtrlBase, String> {

    @Procedure(name="getIcatCtrlBasic")
    fun getIcatCtrlBasic() //: List<IcatCtrlBase>//: IcatCtrlDefault?
}

@Repository
interface PcTeminateRepository: CrudRepository<Terminate, String> {

//    @Procedure(name="getTerminatePassword")
//    fun getTerminatePassword(@Param("str") str: String, @Param("key") key: String): Terminate
    @Query(nativeQuery = true, value = "SELECT [dbo].UFN_ENCRYPT(:P_STR, :P_KEY)")
    fun getTerminatePassword(@Param("P_STR") str: String, @Param("P_KEY") key: String) : String
}

@Repository
interface PcExitpwRepository: CrudRepository<PcExitpw, String> {
    fun findFirstByOrderByEventTime(): PcExitpw
}

//@Repository
//interface PCIcatExceptionRepository: CrudRepository<IcatException, String> {
//
//    @Procedure(name="getIcatException")
//    fun getIcatException(@Param("serial") serial: String) //: List<IcatCtrlBase>//: IcatCtrlDefault?
//}