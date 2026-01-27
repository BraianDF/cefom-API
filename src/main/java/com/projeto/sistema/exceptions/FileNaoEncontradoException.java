package com.projeto.sistema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FileNaoEncontradoException extends RuntimeException {

    public FileNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FileNaoEncontradoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
