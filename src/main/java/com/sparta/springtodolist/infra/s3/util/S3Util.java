package com.sparta.springtodolist.infra.s3.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sparta.springtodolist.global.exception.ErrorCode;
import com.sparta.springtodolist.infra.s3.exception.FileTypeNotAllowedException;
import com.sparta.springtodolist.infra.s3.exception.InvalidReadTypeException;
import com.sparta.springtodolist.infra.s3.exception.NotFoundFileException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public final static String IMAGE_PATH = "cards/";
    private final static String IMAGE_JPG = "image/jpeg";
    private final static String IMAGE_PNG = "image/png";

    public String uploadImage(MultipartFile multipartFile, String filePath) {
        if (!isImageFile(multipartFile)) {
            throw new FileTypeNotAllowedException(ErrorCode.NOT_ALLOWED_FILE_TYPE);
        }
        String fileName = UUID.randomUUID().toString();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3Client.putObject(bucketName, filePath + fileName,
                multipartFile.getInputStream(),
                metadata);
        } catch (Exception e) {
            throw new InvalidReadTypeException(ErrorCode.NOT_READ_TYPE);
        }
        return fileName;
    }

    public String getImagePath(String fileName, String filePath) {
        if (!amazonS3Client.doesObjectExist(bucketName, filePath + fileName)) {
            throw new NotFoundFileException(ErrorCode.NOT_FOUND_FILE);
        }

        return amazonS3Client.getUrl(bucketName, filePath + fileName).toString();
    }

    public void deleteImage(String fileName, String filePath) {
        if (fileName.isBlank() || !amazonS3Client.doesObjectExist(bucketName,
            filePath + fileName)) {
            throw new NotFoundFileException(ErrorCode.NOT_FOUND_FILE);
        }
        amazonS3Client.deleteObject(bucketName, filePath + fileName);
    }

    public boolean existsImage(String fileName, String filePath) {
        return amazonS3Client.doesObjectExist(bucketName, filePath + fileName);
    }

    private Boolean isImageFile(MultipartFile multipartFile) {
        return Objects.equals(multipartFile.getContentType(), IMAGE_JPG) ||
            Objects.equals(multipartFile.getContentType(), IMAGE_PNG);
    }
}
