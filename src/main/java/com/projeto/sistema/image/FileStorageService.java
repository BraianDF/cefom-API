package com.projeto.sistema.image;

import com.projeto.sistema.configuration.FileStoregeConfig;
import com.projeto.sistema.exceptions.FileNaoEncontradoException;
import com.projeto.sistema.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import static com.projeto.sistema.utils.FileNameUtils.gerarNomeFisico;

@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final Path fileStorageLocation;

    public FileStorageService(FileStoregeConfig fileStoregeConfig) {
        this.fileStorageLocation = Paths.get(fileStoregeConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Não foi possivel criar o diretório para salvar os aquivos.",e);
        }
    }

    public String storeFile(MultipartFile file) {
        String nomeOriginal = StringUtils.cleanPath(file.getOriginalFilename());

        if(!isImagemValida(file)) {
            throw new FileStorageException("A imagem não está em um formato suportado.");
        }

        try {
            if (nomeOriginal.contains("..")) {
                throw new FileStorageException("Desculpe! O nome do arquivo contém uma sequência de caminho inválida "+nomeOriginal);
            }

            // Gera o nome físico (único)
            String nomeFisico = gerarNomeFisico(nomeOriginal);

            //Para salvar online, precisa alterar as duas linhas abaixo;
            Path targetLocation = this.fileStorageLocation.resolve(nomeFisico);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nomeFisico;
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível salvar o arquivo "+nomeOriginal+". Tente novamente.",e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            //Para ler online, precisa alterar as duas linhas abaixo;
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNaoEncontradoException("Arquivo chamado "+fileName+" não encontrato.");
            }
        } catch (Exception e) {
            throw new FileNaoEncontradoException("Arquivo chamado "+fileName+" não encontrato.",e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new FileNaoEncontradoException("Arquivo chamado "+fileName+" não encontrato.",e);
        }
    }

    public boolean isImagemValida(MultipartFile image) {
        List<String> contentTypes = Arrays.asList("image/png","image/jpg","image/jpeg");

        for (int i= 0; i < contentTypes.size(); i++) {
            if (image.getContentType().toLowerCase().startsWith(contentTypes.get(i))) {
                return true;
            }
        }

        return false;

        /* Chat me passou esse método
        String contentType = file.getContentType();
        if (!List.of("image/png", "image/jpeg").contains(contentType)) {
            throw new FileStorageException("Tipo de arquivo não permitido");
        }
         */
    }


}
