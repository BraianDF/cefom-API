package com.projeto.sistema.image;

import com.projeto.sistema.model.Inscricao;
import com.projeto.sistema.model.Matricula;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FotoAdolescenteService {
    private final FotoAdolescenteRepository fotoAdolescenteRepository;
    private final FileStorageService fileStorageService;

    public FotoAdolescenteService(FotoAdolescenteRepository fotoAdolescenteRepository, FileStorageService fileStorageService) {
        this.fotoAdolescenteRepository = fotoAdolescenteRepository;
        this.fileStorageService = fileStorageService;
    }

    public void criarFotoAdolescenteInscricao(MultipartFile file, Inscricao inscricao) {
        String fileName = fileStorageService.storeFile(file);
        String fileType = file.getContentType();
        long fileSize = file.getSize();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();

        FotoAdolescente fotoNova = new FotoAdolescente();
        fotoNova.setNomeArquivo(fileName);
        fotoNova.setTipoArquivo(fileType);
        fotoNova.setTamanhoArquivo(fileSize);
        fotoNova.setCaminhoArquivo(fileDownloadUri);

        inscricao.adicionarFoto(fotoNova);
    }

    public void atualizarFotoAdolescenteInscricao(MultipartFile file, Inscricao inscricao) {

        //Busca foto atual e exclui referencia
        FotoAdolescente fotoAtual = inscricao.getFoto();
        inscricao.removerFoto();

        //Salva foto nova
        criarFotoAdolescenteInscricao(file, inscricao);

        //Após salvar com sucesso a foto nova, exclui a foto antiga
        if (fotoAtual != null) {
            fileStorageService.deleteFile(fotoAtual.getNomeArquivo());
        }
    }

    public void criarFotoAdolescenteMatricula(MultipartFile file, Matricula matricula) {
        String fileName = fileStorageService.storeFile(file);
        String fileType = file.getContentType();
        long fileSize = file.getSize();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();

        FotoAdolescente fotoNova = new FotoAdolescente();
        fotoNova.setNomeArquivo(fileName);
        fotoNova.setTipoArquivo(fileType);
        fotoNova.setTamanhoArquivo(fileSize);
        fotoNova.setCaminhoArquivo(fileDownloadUri);

        matricula.adicionarFoto(fotoNova);
    }

    public void atualizarFotoAdolescenteMatricula(MultipartFile file, Matricula matricula) {

        //Busca foto atual e exclui referencia
        FotoAdolescente fotoAtual = matricula.getFoto();
        matricula.removerFoto();

        //Salva foto nova
        criarFotoAdolescenteMatricula(file, matricula);

        //Após salvar com sucesso a foto nova, exclui a foto antiga
        if (fotoAtual != null) {
            fileStorageService.deleteFile(fotoAtual.getNomeArquivo());
        }
    }
}
