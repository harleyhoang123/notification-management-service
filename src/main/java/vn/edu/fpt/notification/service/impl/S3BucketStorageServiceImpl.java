package vn.edu.fpt.notification.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.request.comment.CreateFileRequest;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.service.S3BucketStorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 26/10/2022 - 02:03
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class S3BucketStorageServiceImpl implements S3BucketStorageService {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket}")
    private String bucketAttachFile;

    @Value("${application.cloudfront.bucket}")
    private String cloudfrontBucket;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileCache = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
        String fileKey = UUID.randomUUID().toString();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            File convFile = new File(fileCache);

            try (OutputStream os = Files.newOutputStream(Path.of(convFile.getPath()))) {
                os.write(file.getBytes());
            }
            PutObjectRequest request = new PutObjectRequest(bucketAttachFile, fileKey, convFile);
            request.setMetadata(metadata);
            request.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);
            return fileKey;
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't put object file to AWS S3: " + ex.getMessage());
        } finally {
            try {
                Files.delete(Paths.get(fileCache));
            } catch (Exception ex) {
                log.error("Can't delete converted file: " + ex.getMessage());
            }
        }
    }

    @Override
    public String uploadFile(CreateFileRequest request) {
        byte[] decodedFile = Base64.getDecoder().decode(request.getBase64().getBytes(StandardCharsets.UTF_8));
        String fileKey = UUID.randomUUID().toString();
        InputStream is = new ByteArrayInputStream(decodedFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(request.getMimeType());
        metadata.setContentLength(request.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketAttachFile, fileKey, is, metadata);
        try {
            amazonS3.putObject(putObjectRequest);
        }catch (Exception ex){
            throw new BusinessException("Can't push object to s3 bucket: "+ ex.getMessage());
        }
        return fileKey;
    }

    @Override
    public void downloadFile(String fileKey, HttpServletResponse response) {
        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketAttachFile, fileKey));
            try (InputStream is = s3Object.getObjectContent()) {
                int len;
                byte[] buffer = new byte[4096];
                while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                    response.getOutputStream().write(buffer, 0, len);
                }
            }
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't download file from AWS S3: " + ex.getMessage());
        }
    }

    @Override
    public File downloadFile(String fileKey) {
        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketAttachFile, fileKey));
            try (InputStream is = s3Object.getObjectContent()) {
                File file = new File(fileKey);
                Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return file;
            }
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't download file from AWS S3: " + ex.getMessage());
        }
    }

    @Override
    public String sharingUsingPresignedURL(String fileKey) {
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 24 * 7 * 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        System.out.println("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketAttachFile, fileKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();

    }

    @Override
    public void deleteFile(String fileKey) {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(bucketAttachFile, fileKey);
            amazonS3.deleteObject(request);
        }catch (Exception ex){
            log.error("Can't delete file key: {} in bucket: {}", fileKey, bucketAttachFile);
            throw new BusinessException("Can't delete file in S3 bucket: "+ ex.getMessage());
        }
    }

    @Override
    public String getPublicURL(String fileKey) {
        return String.format("%s/%s", cloudfrontBucket, fileKey);
    }


}
