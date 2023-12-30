package com.example.pzza.controller

import com.example.pzza.models.ReadUserDTO
import com.example.pzza.models.http.DefaultResponse
import com.example.pzza.models.http.ErrorResponse
import com.example.pzza.models.http.SignupRequest
import com.example.pzza.models.http._Error
import com.example.pzza.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import jakarta.validation.UnexpectedTypeException
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService


    @GetMapping(path=["/"])
    fun testController(): ResponseEntity<Any> {
        return ResponseEntity.ok()
            .body(userService.getUsers())
    }

    @PostMapping(path=["/signup"])
    fun signupController(
        @Valid
        @RequestBody
        signupRequest: SignupRequest
    ): ResponseEntity<DefaultResponse> {
        val id = userService.saveUsers(signupRequest)

        return ResponseEntity.ok()
            .header("ACCESS_KEY","${id}")
            .body(DefaultResponse().apply {
                this.message = "회원가입 성공"
            })
    }

    @PostMapping(path=["/login"])
    fun loginController(
        @RequestHeader("ACCESS_KEY")
        token:String
    ): ResponseEntity<DefaultResponse> {
        //로그인 성공 시
        return userService.getUser(token)?.let(ReadUserDTO::id)?.let {
            ResponseEntity.ok().body(
                DefaultResponse().apply {
                    this.message = "로그인 성공"
                }
            )
        // 로그인 실패 시
        }?:kotlin.run {
            ResponseEntity.badRequest().body(
                DefaultResponse().apply {
                    this.message = "로그인에 실패했습니다"
                }
            )
        }
    }

//    DefaultHandlerExceptionResolver


    @ExceptionHandler(value = [UnexpectedTypeException::class])
    fun unexpectedTypeException(
        e:UnexpectedTypeException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val regex = Regex("Check configuration for '([^']+)'")
        val matchResult = regex.find(e.message.toString())
        val field = matchResult?.groupValues?.get(1)

        var errors: MutableList<_Error> = mutableListOf(
            _Error().apply {
                this.message = e.message
                this.field = field
                this.requestValue = "${field} 필드에서 공백이 발생했습니다"
        })

        return ResponseEntity.badRequest().body(
            ErrorResponse().apply {
                this.resultCode="flase"
                this.httpStatus=HttpStatus.BAD_REQUEST.value().toString()
                this.method=request.method.toString()
                this.timestamp= LocalDateTime.now()
                this.path=request.requestURI.toString()
                this.errors= errors
            }
        )
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        e: ConstraintViolationException, request:HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        var errors:MutableList<_Error> = mutableListOf()

        e.constraintViolations.forEach{
            var error:_Error = _Error().apply {
                this.message = it.message.toString()
                this.field = it.propertyPath.last().toString()
                this.requestValue = "너가 입력한 잘못된 값은 ${it.invalidValue} 이야"
            }

            errors.add(error)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse().apply {
                this.resultCode="flase"
                this.httpStatus=HttpStatus.BAD_REQUEST.value().toString()
                this.method=request.method.toString()
                this.timestamp=LocalDateTime.now()
                this.path=request.requestURI.toString()
                this.errors= errors
            }
        )
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        var errors:MutableList<_Error> = mutableListOf()

        e.bindingResult.allErrors.forEach{errorObject->
            var error = _Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.requestValue = (errorObject).rejectedValue
            }
            errors.add(error)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse().apply {
                this.resultCode="flase"
                this.httpStatus=HttpStatus.BAD_REQUEST.value().toString()
                this.method=request.method.toString()
                this.timestamp=LocalDateTime.now()
                this.path=request.requestURI.toString()
                this.errors= errors
            }
        )
    }
}