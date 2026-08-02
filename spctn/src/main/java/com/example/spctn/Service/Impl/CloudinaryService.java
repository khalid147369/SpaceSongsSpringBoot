package com.example.spctn.Service.Impl;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.spctn.Dto.Response.CloudinaryResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Sube un archivo a Cloudinary organizándolo por carpeta.
     * @param file Archivo enviado desde la petición HTTP (MultipartFile)
     * @param folder Nombre de la carpeta destino en Cloudinary
     * @return URL pública del archivo alojado
     */
    public CloudinaryResponse uploadFile(MultipartFile file, String folder) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                "folder", folder,
                "resource_type", "auto" // Detecta automáticamente si es imagen o audio/video
            )
        );
       
        String url = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();
        Double duration = uploadResult.get("duration") != null 
                ? ((Number) uploadResult.get("duration")).doubleValue() 
                        : null;;
        // Retorna la URL segura (HTTPS) para guardar en tu Base de Datos
        return new CloudinaryResponse(url,publicId,duration) ;
    }
    
    public void deleteFile(String publicId, String resourceType) throws IOException {

        cloudinary.uploader().destroy(
            publicId,
            ObjectUtils.asMap(
                "resource_type", resourceType
            )
        );
    }
}
