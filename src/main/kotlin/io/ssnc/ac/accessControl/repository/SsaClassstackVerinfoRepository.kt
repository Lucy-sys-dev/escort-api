package io.ssnc.ac.accessControl.repository

import io.ssnc.ac.accessControl.entity.SsaClaastackVerinfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SsaClassstackVerinfoRepository: CrudRepository<SsaClaastackVerinfo, String> {

}