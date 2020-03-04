package io.ssnc.ac.accessControl.controller

import io.ssnc.ac.accessControl.entity.request.LoginUserRequest
import io.ssnc.ac.accessControl.entity.request.RegisterUserPwRequest
import io.ssnc.ac.accessControl.entity.request.RegisterUserRequest
import io.ssnc.ac.accessControl.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/escort")
class AuthController {
    @Autowired
    lateinit var authService: AuthService

    @PostMapping("{version}/register/user")
    fun registerUser(@RequestBody request: RegisterUserRequest) = ok(authService.registerUser(request))

    @PostMapping("{version}/register/user/pw")
    fun registerUserPw(@RequestBody request: RegisterUserPwRequest) = ok(authService.registerUserPwd(request))

    @GetMapping("{version}/auth/user")
    fun authUser(@RequestBody request: RegisterUserRequest) = ok(authService.authUser(request))

    @PostMapping("{version}/login/user")
    fun loginUser(@RequestBody request: LoginUserRequest) = ok(authService.loginUser(request))



}