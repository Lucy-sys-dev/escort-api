package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.IcatCtrlBase
import io.ssnc.ac.accessControl.entity.IcatCtrlDefault
import io.ssnc.ac.accessControl.entity.IcatException
import org.springframework.data.jpa.repository.JpaRepository
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
interface PCIcatExceptionRepository: CrudRepository<IcatException, String> {

    @Procedure(name="getIcatException")
    fun getIcatException(@Param("serial") serial: String) //: List<IcatCtrlBase>//: IcatCtrlDefault?
}