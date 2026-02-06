package com.projeto.cefom.exceptions;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String mensagem) {
        super(mensagem);
    }

    public FileStorageException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
