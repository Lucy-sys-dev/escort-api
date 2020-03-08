package io.ssnc.ac.escort.controller

import io.ssnc.ac.escort.entity.request.LogRequest
import io.ssnc.ac.escort.service.AgentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.io.File
import java.net.URLEncoder
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/escort/access-control")
class AgentController {
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
    lateinit var agentService: AgentService

    @GetMapping("/version")
    fun getVersion() = ResponseEntity.status(HttpStatus.OK).body(agentService.getVersion())

    @PostMapping("/{appId}/download")
    fun downloadAppFiles(@PathVariable(value="appId") appId: String, response: HttpServletResponse) : ResponseEntity<Resource> {
        val filename =
            when (appId) {
                "site" -> site
                "sitecsinfo" -> sitecsinfo
                "plp" -> plp
                "plpcsinfo" -> plpcsinfo
                else -> null
            }

        val file = getValidFile(path+filename)
        agentService.checkFile(file)

        val encodedFileName = URLEncoder.encode(file.name, "UTF-8")

        val headers = HttpHeaders()
        headers.contentType = MediaType.parseMediaType("application/x-download;charset=utf-8")
        headers.contentLength = file.length()
        headers.contentDisposition = ContentDisposition.builder("attachment").filename(encodedFileName).build()
        return ResponseEntity.ok().headers(headers).body(InputStreamResource(file.inputStream()))
    }

    @PostMapping("/log")
    fun createLog(@RequestBody request: LogRequest) : ResponseEntity<*> {
        agentService.createLog(request)
        return ResponseEntity.status(HttpStatus.CREATED).build<Any>()
    }

    private fun getValidFile(path: String?) = File(stripRelative(path.orEmpty()))

    private companion object {
        fun prependSlash(path: String): String = if (path.isEmpty() || path[0] == '/') path else "/$path"
        fun stripRelative(path: String): String = path.split('/').filter { it != "." && it != ".." }.joinToString("/")
    }
}