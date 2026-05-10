package com.sdp1617.webserver.domain.portfolio.application;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sdp1617.webserver.domain.portfolio.application.exception.PortfolioErrorCode;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private static final List<String> ALLOWED_EXTENSIONS = List.of("pdf", "zip", "png", "jpg", "jpeg");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String dirName) {
        String extension = validateFile(file);

        String s3Key = dirName + "/" + UUID.randomUUID() + "." + extension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(toSafeContentType(extension));

        try {
            amazonS3Client.putObject(bucket, s3Key, file.getInputStream(), metadata);
        } catch (IOException | AmazonClientException e) {
            log.error("S3 파일 업로드 실패: key={}, error={}", s3Key, e.getMessage(), e);
            throw new ApplicationException(PortfolioErrorCode.FILE_UPLOAD_FAILED);
        }

        return amazonS3Client.getUrl(bucket, s3Key).toString();
    }

    private String toSafeContentType(String extension) {
        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "zip" -> "application/zip";
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            default -> "application/octet-stream";
        };
    }

    private String validateFile(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ApplicationException(PortfolioErrorCode.FILE_SIZE_EXCEEDED);
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new ApplicationException(PortfolioErrorCode.INVALID_FILE_TYPE);
        }

        return extension.toLowerCase();
    }
}
