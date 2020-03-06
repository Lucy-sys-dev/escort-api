package io.ssnc.ac.accessControl.controller

//import io.ssnc.ac.accessControl.repository.PCIcatRepository
import io.ssnc.ac.accessControl.entity.IcatCtrlBase
import io.ssnc.ac.accessControl.entity.IcatCtrlDefault
import io.ssnc.ac.accessControl.entity.request.AccessControlRequest
import io.ssnc.ac.accessControl.entity.request.LogRequest
import io.ssnc.ac.accessControl.repository.PCIcatBasicRepository
import io.ssnc.ac.accessControl.repository.PCIcatDefaultRepository
import io.ssnc.ac.accessControl.service.PCExceptionService
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.net.URLEncoder
import java.nio.file.Files
import java.nio.file.Paths
import javax.persistence.EntityManager
import javax.persistence.StoredProcedureQuery
import javax.servlet.http.HttpServletResponse
import javax.xml.ws.Service

@RestController
@RequestMapping("/escort/access-control")
class MainController {
    @Value("\${downloadfile.path}")
    private val path: String = "C:\\text\\"

    @Value("\${downloadfile.site}")
    private val site: String = "site.edcfg"

    @Value("\${downloadfile.sitecsinfo}")
    private val sitecsinfo: String = "sitecsinfo.cscfg"

    @Value("\${downloadfile.plp}")
    private val plp: String = "plp.edcfg"

    @Value("\${downloadfile.plpcsinfo}")
    private val plpcsinfo: String = "plpcsinfo.cscfg"


    @Autowired
    lateinit var pcExceptionService: PCExceptionService

//    @GetMapping("/")
//    fun swagger(response: HttpServletResponse) = response.sendRedirect("$servletContextPath/swagger-ui.html")

    @GetMapping("/version")
    fun getVersion()
            = ResponseEntity.status(OK).body(pcExceptionService.getVersion())

    @PostMapping("/{appId}/download")
    fun downloadAppFiles(@PathVariable(value="appId") appId: String, response: HttpServletResponse) : ResponseEntity<Resource> {

        var filename =
            when (appId) {
                "site" -> site
                "sitecsinfo" -> sitecsinfo
                "plp" -> plp
                "plpcsinfo" -> plpcsinfo
                else -> null
            }

        val file = getValidFile(path+filename)
        pcExceptionService.checkFile(file)

        val encodedFileName = URLEncoder.encode(file.name, "UTF-8")

        val headers = HttpHeaders()
        headers.contentType = MediaType.parseMediaType("application/x-download;charset=utf-8")
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(encodedFileName).build()
        return ResponseEntity.ok().headers(headers).body(InputStreamResource(file.inputStream()))
    }

    @GetMapping("/search/{serial}")
    fun search(@PathVariable(value="serial") serial: String)
            = ResponseEntity.status(OK).body(pcExceptionService.search(serial))

    @PostMapping("/log")
    fun createLog(@RequestBody request: LogRequest) : ResponseEntity<*> {
        pcExceptionService.createLog(request)
        return ResponseEntity.status(CREATED).build<Any>()
    }

    @PostMapping("")
    fun createAcessControl(@RequestBody request: AccessControlRequest): ResponseEntity<*> {
        pcExceptionService.createAccessControls(request)
        return ResponseEntity.status(CREATED).build<Any>()
    }

    private fun getValidFile(path: String?) = File(stripRelative(path.orEmpty()))

    private companion object {
        fun prependSlash(path: String): String = if (path.isEmpty() || path[0] == '/') path else "/$path"
        fun stripRelative(path: String): String = path.split('/').filter { it != "." && it != ".." }.joinToString("/")
    }
}