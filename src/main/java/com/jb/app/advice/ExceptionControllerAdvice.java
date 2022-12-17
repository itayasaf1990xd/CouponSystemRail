package com.jb.app.advice;

import com.jb.app.exceptions.CouponSysException;
import com.jb.app.exceptions.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AdviceInfo handlerSql(SQLException e) {
        return new AdviceInfo("error sql", e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AdviceInfo handlerNotFound() {
        return new AdviceInfo(HttpStatus.NOT_FOUND.name(), ExceptionType.HTTP_NOT_FOUND.getMsg());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AdviceInfo handlerMissingMethod(MethodArgumentTypeMismatchException e) {
        return new AdviceInfo(e.getName(), e.getMessage());
    }

    @ExceptionHandler(CouponSysException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//////
    public AdviceInfo handlerInternal(CouponSysException e) {
        return new AdviceInfo("coupon exception", e.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AdviceInfo handlerMissingHeader(MissingRequestHeaderException e) {
        return new AdviceInfo(e.getHeaderName(), e.getMessage());
    }

    /*@ExceptionHandler(NoHandlerFoundException.class) // serious api
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handlerNotFound() {
        Map<String,Object> body = new HashMap<>();  // LinkedHashMap is faster to add new objects than other
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
*/

}
