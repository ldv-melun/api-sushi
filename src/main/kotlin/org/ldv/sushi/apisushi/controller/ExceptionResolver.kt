package org.ldv.sushi.apisushi.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class ExceptionResolver {
    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoHandlerFound(e: NoHandlerFoundException, request: WebRequest?): HashMap<String, String> {
        val response = HashMap<String, String>()
        response["status"] = "fail"
        response["message"] = e.localizedMessage
        return response
    }
}
