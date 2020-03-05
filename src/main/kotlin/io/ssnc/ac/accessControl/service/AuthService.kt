package io.ssnc.ac.accessControl.service

import io.ssnc.ac.accessControl.entity.PcBasic
import io.ssnc.ac.accessControl.entity.pcUsers
import io.ssnc.ac.accessControl.entity.pcUsersPK
import io.ssnc.ac.accessControl.entity.request.LoginUserRequest
import io.ssnc.ac.accessControl.entity.request.RegisterUserPwRequest
import io.ssnc.ac.accessControl.exception.NotFoundException
import io.ssnc.ac.accessControl.entity.request.RegisterUserRequest
import io.ssnc.ac.accessControl.entity.response.LoginResponse
import io.ssnc.ac.accessControl.exception.LoginException
import io.ssnc.ac.accessControl.repository.InsainfoRepository
import io.ssnc.ac.accessControl.repository.PCIcatBasicRepository
import io.ssnc.ac.accessControl.repository.PcBasicRepository
import io.ssnc.ac.accessControl.repository.PcUsersReposiroty
import io.ssnc.ac.accessControl.util.DateUtil
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Period
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

        val new = pcUsers(pk = pk, hname = user.hname, regDt = Date(), password = null)
        pcUsersRepository.save(new)
    }

    @Throws(NotFoundException::class)
    fun registerUserPwd(request: RegisterUserPwRequest){
        val pk = pcUsersPK(empno = request.id, affiliate = request.affiliate)
        val user = pcUsersRepository.findByPk(pk) ?: throw NotFoundException("empno is not found")
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
}