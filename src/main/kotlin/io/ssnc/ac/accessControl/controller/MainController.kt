package io.ssnc.ac.accessControl.controller

//import io.ssnc.ac.accessControl.repository.PCIcatRepository
import io.ssnc.ac.accessControl.entity.IcatCtrlBase
import io.ssnc.ac.accessControl.entity.IcatCtrlDefault
import io.ssnc.ac.accessControl.entity.request.LogRequest
import io.ssnc.ac.accessControl.repository.PCIcatBasicRepository
import io.ssnc.ac.accessControl.repository.PCIcatDefaultRepository
import io.ssnc.ac.accessControl.service.PCExceptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.StoredProcedureQuery
import javax.xml.ws.Service

@RestController
@RequestMapping("/escort/access-control")
class MainController {

    @Autowired
    lateinit var pcExceptionService: PCExceptionService

//    @GetMapping("/")
//    fun swagger(response: HttpServletResponse) = response.sendRedirect("$servletContextPath/swagger-ui.html")

    @GetMapping("/version")
    fun getVersion()
            = ResponseEntity.status(OK).body("1.0")

    @GetMapping("/search/{serial}")
    fun search(@PathVariable(value="serial") serial: String)
            = ResponseEntity.status(OK).body(pcExceptionService.search(serial))

    @PostMapping("/log")
    fun createLog(@RequestBody request: LogRequest) : ResponseEntity<*> {
        pcExceptionService.createLog(request)
        return ResponseEntity.status(CREATED).build<Any>()
    }
}