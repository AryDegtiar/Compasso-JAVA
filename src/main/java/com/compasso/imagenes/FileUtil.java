package com.compasso.imagenes;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Path.of("uploads");

        String fileCode = System.currentTimeMillis() + "_" + fileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Error al cargar el archivo " + fileName, e);
        }

        return fileCode;
    }

    private Path foundFile;

    public Resource getFileAsResource(String fileCode) throws IOException {
        Path uploadPath = Path.of("uploads");
        Files.list(uploadPath).forEach(file -> {
            if (file.getFileName().toString().equals(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

}
