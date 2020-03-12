package io.ssnc.ac.escort.controller

//import io.ssnc.ac.accessControl.repository.PCIcatRepository
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.service.PCExceptionService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/escort/access-control")
class MainController {

    @Autowired
    lateinit var pcExceptionService: PCExceptionService

//    @GetMapping("/")
//    fun swagger(response: HttpServletResponse) = response.sendRedirect("$servletContextPath/swagger-ui.html")

    @GetMapping("/search/{serial}")
    fun search(@PathVariable(value="serial") serial: String)
            = ResponseEntity.status(OK).body(pcExceptionService.searchPcIcat(serial))

    @PostMapping("")
    fun createAcessControl(@RequestBody request: AccessControlRequest): ResponseEntity<*> {
        pcExceptionService.createAccessControls(request)
        return ResponseEntity.status(CREATED).build<Any>()
    }


}