package com.matera.cursoferias.petstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.matera.cursoferias.petstore.service.dto.ErroResponseDTO;
import com.matera.cursoferias.petstore.util.exception.BusinessException;
import com.matera.cursoferias.petstore.util.exception.RegistroNaoEncontradoException;

public class ControllerBase {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ Exception.class })
    public ErroResponseDTO handleException(Exception e) {
        return ErroResponseDTO.withError("Erro inesperado ao executar a operação! " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ErroResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErroResponseDTO.withError(e.getBindingResult().getFieldErrors());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ BusinessException.class })
    public ErroResponseDTO handleBusinessException(BusinessException e) {
        return ErroResponseDTO.withError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ RegistroNaoEncontradoException.class })
    public ErroResponseDTO handleRegistroNaoEncontradoException(RegistroNaoEncontradoException e) {
        return ErroResponseDTO.withError(e.getMessage());
    }

}
