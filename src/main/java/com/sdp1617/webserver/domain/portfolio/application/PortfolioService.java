package com.sdp1617.webserver.domain.portfolio.application;

import com.sdp1617.webserver.domain.apply.entity.Apply;
import com.sdp1617.webserver.domain.apply.infrastructure.ApplyRepository;
import com.sdp1617.webserver.domain.portfolio.application.dto.response.PortfolioUploadResponse;
import com.sdp1617.webserver.domain.portfolio.application.exception.PortfolioErrorCode;
import com.sdp1617.webserver.domain.portfolio.entity.Portfolio;
import com.sdp1617.webserver.domain.portfolio.infrastructure.PortfolioRepository;
import com.sdp1617.webserver.global.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final ApplyRepository applyRepository;
    private final PortfolioRepository portfolioRepository;
    private final S3Uploader s3Uploader;

    public PortfolioUploadResponse upload(Long applicationId, MultipartFile file) {
        Apply apply = applyRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(PortfolioErrorCode.APPLICATION_NOT_FOUND));

        if (portfolioRepository.existsByApplicationId(applicationId)) {
            throw new ApplicationException(PortfolioErrorCode.PORTFOLIO_ALREADY_EXISTS);
        }

        String fileUrl = s3Uploader.upload(file, "portfolio");

        try {
            Portfolio portfolio = portfolioRepository.save(Portfolio.builder()
                    .application(apply)
                    .fileUrl(fileUrl)
                    .fileName(file.getOriginalFilename() != null ? file.getOriginalFilename() : "")
                    .build());
            return new PortfolioUploadResponse(portfolio.getId(), portfolio.getFileUrl(), portfolio.getFileName());
        } catch (DataIntegrityViolationException e) {
            throw new ApplicationException(PortfolioErrorCode.PORTFOLIO_ALREADY_EXISTS);
        }
    }
}
