package com.compasso.controlador;

import com.compasso.imagenes.FileUploadResponse;
import com.compasso.imagenes.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://compasso-java-production.up.railway.app")
@RequestMapping("/files")
public class ControladorFile {

    @PostMapping(path = {"","/"})
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

        String fileCode = FileUtil.saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(String.valueOf(size));
        response.setDownloadUri("/files/dowloadFile/" + fileCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = {"/dowloadFile/{fileCode}"})
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        FileUtil fileUtil = new FileUtil();

        Resource resource = null;

        try {
            resource = fileUtil.getFileAsResource(fileCode);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (resource == null) {
            return new ResponseEntity<>("no se encontro el archivo",HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = String.format("attachment; filename=\"" + resource.getFilename() + "\"");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
