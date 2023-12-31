package com.example.pzza.handler

import com.example.pzza.models.http.ErrorResponse
import com.example.pzza.models.http._Error
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApiErrorController {
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        e: ConstraintViolationException, request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        var errors:MutableList<_Error> = mutableListOf()

        e.constraintViolations.forEach{
            var error: _Error = _Error().apply {
                this.message = it.message.toString()
                this.field = it.propertyPath.last().toString()
                this.requestValue = "너가 입력한 잘못된 값은 ${it.invalidValue} 이야"
            }

            errors.add(error)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse().apply {
                this.resultCode="flase"
                this.httpStatus= HttpStatus.BAD_REQUEST.value().toString()
                this.method=request.method.toString()
                this.timestamp= LocalDateTime.now()
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
                this.httpStatus= HttpStatus.BAD_REQUEST.value().toString()
                this.method=request.method.toString()
                this.timestamp= LocalDateTime.now()
                this.path=request.requestURI.toString()
                this.errors= errors
            }
        )
    }
}