package com.projeto.cefom.exceptions;

public class FileNaoEncontradoException extends RuntimeException {

    public FileNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FileNaoEncontradoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
