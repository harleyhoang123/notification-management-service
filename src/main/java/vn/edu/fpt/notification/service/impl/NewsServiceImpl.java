package vn.edu.fpt.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.entity.News;
import vn.edu.fpt.notification.entity._Attachment;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.NewsRepository;
import vn.edu.fpt.notification.service.NewsService;
import vn.edu.fpt.notification.service.S3BucketStorageService;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final S3BucketStorageService s3BucketStorageService;


    @Override
    public void createNews(CreateNewsRequest request) {
        if(newsRepository.findByTitle(request.getTitle()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News title already exist");
        }

        String fileName = request.getThumbnail().getOriginalFilename();
        String fileKey = UUID.randomUUID() + fileName;

        String url = s3BucketStorageService.uploadFile(fileKey, fileName, request.getThumbnail());
        _Attachment attachment = _Attachment.builder()
                .fileName(fileName)
                .fileKey(fileKey)
                .build();

        News news = News.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .thumbnail()
                .build();
        try {
            newsRepository.save(news);
            log.info("Create new success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save news to database: " + ex.getMessage());
        }
    }

    @Override
    public void updateNews(String newsId, UpdateNewsRequest request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News ID not exist"));
        if (Objects.nonNull(request.getTitle())) {
            news.setTitle(request.getTitle());
        }
        if (Objects.nonNull(request.getContent())) {
            news.setContent(request.getContent());
        }
        try {
            newsRepository.save(news);
            log.info("Update news success");
        } catch (Exception ex) {
            throw new BusinessException("Can't update news to database: " + ex.getMessage());
        }
    }

    @Override
    public void deleteNews(String newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News ID not exist"));
        try {
            newsRepository.deleteById(newsId);
            log.info("Delete news success");
        } catch (Exception ex) {
            throw new BusinessException("Can't delete news to database: " + ex.getMessage());
        }
    }

    @Override
    public GetNewsDetailResponse getNewsDetailResponse(String newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News ID not exist"));
        Integer currentViews = news.getViews();
        GetNewsDetailResponse getNewsDetailResponse = GetNewsDetailResponse.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .content(news.getContent())
                .thumbnail(news.getThumbnail())
                .views(news.getViews())
                .comments(news.getComments())
                .build();
        news.setViews(currentViews + 1);
        try {
            newsRepository.save(news);
            log.info("Increase views of news success");
        } catch (Exception ex) {
            throw new BusinessException("Can't increase views of news in database: " + ex.getMessage());
        }
        return getNewsDetailResponse;
    }
}
