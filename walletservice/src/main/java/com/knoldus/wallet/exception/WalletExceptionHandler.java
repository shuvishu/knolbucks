package com.knoldus.wallet.exception;

import com.knoldus.wallet.model.Error;
import com.knoldus.wallet.model.ResponseBody;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class WalletExceptionHandler {

    @ExceptionHandler(value = UserDoesNotExistException.class)
    public ResponseEntity<ResponseBody> handleCustomException(Exception ex) {

        ResponseBody responseBody = ResponseBody
                .<Object>builder()
                .status("failed")
                .errors(Collections
                        .singletonList(Error.builder()
                                .errorMessage(ex.getMessage())
                                .id(UUID.randomUUID().toString())
                                .errorCode(HttpStatus.NOT_FOUND.value())
                                .time(LocalDateTime.now().toString())
                                .build()))
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ResponseBody> constraintViolationException(Exception ex) {

        WebExchangeBindException methodArgumentNotValidException = (WebExchangeBindException) ex;

        List<String> errorMessage = methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseBody responseBody = ResponseBody
                .<Object>builder()
                .errors(errorMessage.stream()
                        .map(message -> Error.builder()
                                .time(LocalDateTime.now().toString())
                                .errorMessage(message)
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .id(UUID.randomUUID().toString())
                                .time(LocalDateTime.now().toString()).build())
                        .collect(Collectors.toList()))
                .status("failed")
                .build();

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletRequestAlreadyPendingException.class)
    public ResponseEntity<ResponseBody> WalletRequestAlreadyPending(Exception ex) {

        ResponseBody responseBody = ResponseBody
                .<Object>builder()
                .status("failed")
                .errors(Collections
                        .singletonList(Error.builder()
                                .errorMessage(ex.getMessage())
                                .id(UUID.randomUUID().toString())
                                .errorCode(HttpStatus.CONFLICT.value())
                                .time(LocalDateTime.now().toString())
                                .build()))
                .build();
        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);

    }
}
