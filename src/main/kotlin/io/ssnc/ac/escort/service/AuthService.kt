package io.ssnc.ac.escort.service

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
import io.ssnc.ac.escort.repository.InsainfoRepository
import io.ssnc.ac.escort.repository.PcBasicRepository
import io.ssnc.ac.escort.repository.PcUsersReposiroty
import io.ssnc.ac.escort.util.DataUtil
import io.ssnc.ac.escort.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService {
    companion object : KLogging()

    @Autowired
    lateinit var insainfoRepository: InsainfoRepository

    @Autowired
    lateinit var pcBasicRepository: PcBasicRepository

    @Autowired
    lateinit var pcUsersRepository: PcUsersReposiroty

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
        val user = pcUsersRepository.findByPk(pk) ?: throw NotFoundException("empno is not found")
        if (user.password == request.pwd) throw PasswordException("Please enter valid password")
        if (!DataUtil.isValidPassword(request.pwd)) throw PasswordException("Please enter valid password")
        user.password = request.pwd
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

        if (user.password != request.pwd)
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
}