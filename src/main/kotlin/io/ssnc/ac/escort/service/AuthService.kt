package io.ssnc.ac.escort.service

import io.ssnc.ac.escort.entity.PcBasic
import io.ssnc.ac.escort.entity.pcUsers
import io.ssnc.ac.escort.entity.pcUsersPK
import io.ssnc.ac.escort.entity.request.LoginUserRequest
import io.ssnc.ac.escort.entity.request.RegisterUserPwRequest
import io.ssnc.ac.escort.exception.NotFoundException
import io.ssnc.ac.escort.entity.request.RegisterUserRequest
import io.ssnc.ac.escort.entity.request.StatusUserRequest
import io.ssnc.ac.escort.entity.response.LoginResponse
import io.ssnc.ac.escort.entity.response.StatusResponse
import io.ssnc.ac.escort.exception.LoginException
import io.ssnc.ac.escort.exception.PasswordException
import io.ssnc.ac.escort.repository.HbCompanyReposiroty
import io.ssnc.ac.escort.repository.InsainfoRepository
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PcUsersReposiroty
import io.ssnc.ac.escort.util.DataUtil
import io.ssnc.ac.escort.util.DateUtil
import io.ssnc.ac.escort.util.EnDecryptHelper
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Service
class AuthService {
    companion object : KLogging()

    @Autowired
    lateinit var insainfoRepository: InsainfoRepository

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcUsersRepository: PcUsersReposiroty

    @Autowired
    lateinit var hbCompanyReposiroty: HbCompanyReposiroty

    @Throws(NotFoundException::class)
    fun registerUser(request: RegisterUserRequest){
        val user = insainfoRepository.findByEmpno(request.affiliate+request.id) ?: throw NotFoundException("empno is not found")
        if (user.resno != request.authentication_code)
            throw LoginException("auth code is fail")
        val pk = pcUsersPK(empno = user.empno.substring(2, 9), affiliate = user.empno.substring(0,2))

        pcUsersRepository.findByPk(pk)?.let {exist ->
            exist.deptCode = user.deptcode
            exist.deptName = user.sdeptnm
            exist.resno = user.resno
            exist.empType = user.emptype
            exist.status = user.status
            exist.companyName = user.companyname

            pcUsersRepository.save(exist)
        } ?: run {
            val new = pcUsers(pk = pk, hname = user.hname, regDt = Date(), password = null, deptCode = user.deptcode, deptName = user.sdeptnm,
                resno = user.resno, empType = user.emptype, status = user.status, companyName = user.companyname)
            pcUsersRepository.save(new)
        }
    }

    @Throws(NotFoundException::class)
    fun registerUserPwd(request: RegisterUserPwRequest){
        val pk = pcUsersPK(empno = request.id, affiliate = request.affiliate)
        val user = pcUsersRepository.findByPk( pk) ?: throw NotFoundException("empno is not found")
        if (user.password == request.pwd) throw PasswordException("Please enter valid password")
        if (!DataUtil.isValidPassword(request.pwd)) throw PasswordException("Please enter valid password")
//        user.password = passwordEncoder.encode(request.pwd)
        user.changePwdDt = Date()
        pcUsersRepository.save(user)
    }

    @Throws(LoginException::class)
    fun authUser(request: RegisterUserRequest) {
        val user = insainfoRepository.findByEmpno(request.affiliate+request.id) ?: throw NotFoundException("empno is not found")
        if (user.resno != request.authentication_code)
            throw LoginException("auth code is fail")
    }

    @Throws(LoginException::class)
    fun loginUser(request: LoginUserRequest) : LoginResponse {
        val pk = pcUsersPK(empno = request.id, affiliate = request.affiliate)
        val user = pcUsersRepository.findByPk(pk) ?: throw NotFoundException("empno is not found")

        val password = user.password

        if (password != request.pwd)
            throw LoginException("pwd is fail")

        if (user.changePwdDt?.let { it1 -> DateUtil.convertToLocalDateViaInstant(it1)?.let { it1 -> DateUtil.addMonth(it1, 3) } }!! <= DateUtil.convertToLocalDateViaInstant(
                user.changePwdDt!!)) {
            throw LoginException("pwd is changed")
        }
        return LoginResponse(affiliate = user.pk!!.affiliate!!, id = user.pk.empno!!,
            name = user.hname!!, dept_code = user.deptCode!!, emp_type = user.empType!!,
            status = user.status!!, company_name = user.companyName!!, dept_name = user.deptName!!)
    }

    @Throws(LoginException::class)
    fun statusUser(request: StatusUserRequest) : StatusResponse {
        val pk = pcUsersPK(empno = request.id, affiliate = request.affiliate)
        val user = pcUsersRepository.findByPk(pk) ?: throw NotFoundException("empno is not found")

        return StatusResponse(affiliate = user.pk!!.affiliate!!, id = user.pk.empno!!, status = user.status!!)
    }

    fun searchAffiliate(): HashMap<String, HashMap<String, String>> {
//        val result = HashMap<String, ArrayList<HashMap<String, String>>>()
        val result = HashMap<String, HashMap<String, String>>()
        //val result = {};
        hbCompanyReposiroty.findAll().forEach { it ->
            val data = hbCompanyReposiroty.findByCompanyCode(it.companyCode)
            val detail = HashMap<String, String>()
            detail.put("KOR", data.companyName!!)
            detail.put("ENG", data.companyEngname!!)
            result.put(it.companyCode, detail)
        }
        return result
    }

    fun getUserById(empno: String): List<PcBasic>? {
        return pcBasicRepository.findByEmpno(empno)
    }

    fun passwordEndecrypt(text: String) {
        val encrypt = EnDecryptHelper.encryptText(text)
        logger.debug { "passwordEncrypt() $encrypt" }
        val decrypt = EnDecryptHelper.decryptText(encrypt)
        logger.debug { "passwordDecrypt() $decrypt" }

    }
}