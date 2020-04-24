package io.ssnc.ac.escort.controller

//import io.ssnc.ac.accessControl.repository.PCIcatRepository
import io.ssnc.ac.escort.entity.request.AccessControlRequest
import io.ssnc.ac.escort.entity.request.CreateUsbDeviceRequest
import io.ssnc.ac.escort.service.PCExceptionService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/escort")
class MainController {

    @Autowired
    lateinit var pcExceptionService: PCExceptionService

//    @GetMapping("/")
//    fun swagger(response: HttpServletResponse) = response.sendRedirect("$servletContextPath/swagger-ui.html")

    @GetMapping("/access-control/search/{serial}")
    fun searchPcIcat(@PathVariable(value="serial") serial: String)
            = ResponseEntity.status(OK).body(pcExceptionService.searchPcIcat(serial))

    @PostMapping("/{v1}/access-control")
    fun createAcessControl(@RequestBody request: AccessControlRequest): ResponseEntity<*> {
        pcExceptionService.createAccessControls(request)
        pcExceptionService.sendEscortClient(request.serial)
        return ResponseEntity.status(CREATED).build<Any>()
    }

    @GetMapping("/{v1}/access-control/{empno}")
    fun searchSerial(@PathVariable(value="v1") v1: String, @PathVariable(value="empno") empno: String)
            = ResponseEntity.status(OK).body(pcExceptionService.searchEmpno(empno))

    @PostMapping("/{v1}/access-control/usb")
    fun createUsbDevice(@RequestBody request: CreateUsbDeviceRequest) : ResponseEntity<*> {
        pcExceptionService.createUsbDevice(request)
        return ResponseEntity.status(CREATED).build<Any>()
    }

    @PostMapping("/{v1}/access-control/send/{serial}")
    fun sendAccessControl(@PathVariable(value="serial")serial: String) : ResponseEntity<*> {
        pcExceptionService.sendEscortClient(serial)
        return ResponseEntity.status(OK).build<Any>()
    }

}