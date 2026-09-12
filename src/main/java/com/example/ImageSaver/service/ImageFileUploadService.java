package com.example.ImageSaver.service;

import com.example.ImageSaver.models.*;
import com.example.ImageSaver.repository.CategoryModelRepository;
import com.example.ImageSaver.repository.ImageFileUploadRepository;
import com.example.ImageSaver.repository.TagModelRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class ImageFileUploadService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    Path originalStorageDirectory= Paths.get("./uploaded/originals/");
    Path thumbnailStorageDirectory=Paths.get("./uploads/thumbnails/");

    public void saveImageFileRequest(ImageUploadRequest imageUploadRequest) throws IOException {
        ImageUpload uploadImage=new ImageUpload();

        uploadImage.setTitle(imageUploadRequest.getTitle());
        uploadImage.setDiscription(imageUploadRequest.getDescription());


        Category category=imageUploadRequest.getCategory();
        if(category != null){
            uploadImage.setCategory(imageUploadRequest.getCategory());
            categoryModelRepository.save(category);
        }


        Tag tag=imageUploadRequest.getTag();
        if(tag!=null){
            uploadImage.getTag().add(tag);
            tagModelRepository.save(tag);

        }


//        //create folder if not exist
//        if( !Files.exists(originalStorageDirectory)){
//            Files.createDirectory(originalStorageDirectory);
//        }
//        if(!Files.exists(thumbnailStorageDirectory)){
//            Files.createDirectory(thumbnailStorageDirectory);
//        }

        Files.createDirectories(originalStorageDirectory);
        Files.createDirectories(thumbnailStorageDirectory);


        // get file from request
        MultipartFile file=imageUploadRequest.getFiles();
        System.out.println(file.getOriginalFilename());



        //1.generate unique file name and save original file to folder
        String uniqueFileName= UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path destinationPathOfOriginalUniqueFile=originalStorageDirectory.resolve(uniqueFileName);
        Files.copy(file.getInputStream() , destinationPathOfOriginalUniqueFile , StandardCopyOption.REPLACE_EXISTING);




      // 2. generate thumbnail file and save it to folder
        String thumbnailFileName="Compressed_" +uniqueFileName;

        Path destinationPathOfCompressFile =  thumbnailStorageDirectory.resolve(thumbnailFileName);
        try {
            // 3. Compress and write directly to the final destination path
            Thumbnails.of(destinationPathOfCompressFile.toFile())
                    .scale(1.0)
                    .outputQuality(0.6)
                    .toFile(destinationPathOfCompressFile.toFile());

        } catch (IOException e) {
            e.printStackTrace();
        }

















        //convert it into url
         String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(uniqueFileName)
                .toUriString();

        String compressUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path( thumbnailFileName )
                .toUriString();


        uploadImage.setImageUrl(downloadUrl);
        uploadImage.setThumbnailUrl(compressUrl);

        imageFileUploadRepository.save(uploadImage);


    }


    public  ListImageResponse searchImageByTitle(String imageTitle){

        ListImageResponse response=new ListImageResponse();

        ImageUpload imageData=imageFileUploadRepository.findByTitle(imageTitle);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag().toString());
        response.setCategory(imageData.getCategory().getName());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }

    public  ListImageResponse searchImageByTag(String tag){

        ListImageResponse response=new ListImageResponse();

        ListImageResponse imageData=tagModelRepository. findByName(tag);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag());
        response.setCategory(imageData.getCategory() );
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }

    public  ListImageResponse searchImageByCategory(String category){

        ListImageResponse response=new ListImageResponse();

        ListImageResponse imageData=categoryModelRepository.findByName(category);

        response.setTitle(imageData.getTitle());
        response.setTag(imageData.getTag());
        response.setCategory(imageData.getCategory());
        response.setThumbnailUrl(imageData.getThumbnailUrl());

        return  response;

    }



 }


