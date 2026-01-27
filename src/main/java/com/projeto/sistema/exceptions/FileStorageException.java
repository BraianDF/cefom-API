package com.projeto.sistema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String mensagem) {
        super(mensagem);
    }

    public FileStorageException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
