package vn.edu.fpt.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.cache.UserInfo;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.GetNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.CreateNewsResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsResponse;
import vn.edu.fpt.notification.entity.News;
import vn.edu.fpt.notification.entity._Attachment;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.AttachmentRepository;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.NewsRepository;
import vn.edu.fpt.notification.service.NewsService;
import vn.edu.fpt.notification.service.S3BucketStorageService;
import vn.edu.fpt.notification.service.UserInfoService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final S3BucketStorageService s3BucketStorageService;
    private final AttachmentRepository attachmentRepository;
    private final UserInfoService userInfoService;
    private final MongoTemplate mongoTemplate;


    @Override
    public CreateNewsResponse createNews(CreateNewsRequest request) {
        if(newsRepository.findByTitle(request.getTitle()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News title already exist");
        }

        String fileKey = s3BucketStorageService.uploadFile(request.getThumbnail());
        _Attachment attachment = _Attachment.builder()
                .fileKey(fileKey)
                .build();
        try {
            attachment = attachmentRepository.save(attachment);
        }catch (Exception ex){
            throw new BusinessException("Can't save attachment to database: "+ ex.getMessage());
        }

        News news = News.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .thumbnail(attachment)
                .build();
        try {
            news = newsRepository.save(news);
            log.info("Create new success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save news to database: " + ex.getMessage());
        }

        return CreateNewsResponse.builder()
                .newsId(news.getNewsId())
                .build();
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
        String author = userInfoService.getUserInfo(news.getCreatedBy()).getFullName();

        GetNewsDetailResponse getNewsDetailResponse = GetNewsDetailResponse.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .content(news.getContent())
                .views(news.getViews())
                .comments(news.getComments())
                .author(author)
                .createdDate(news.getCreatedDate())
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

    @Override
    public PageableResponse<GetNewsResponse> getNews(GetNewsRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(request.getTitle()));
        }
        query.with(Sort.by(Sort.Direction.DESC, "created_date"));

        Long totalElements = mongoTemplate.count(query, News.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);

        List<News> news = mongoTemplate.find(query, News.class);

        List<GetNewsResponse> getNewsResponses = news.stream().map(this::convertNewsToGetNewsResponse).collect(Collectors.toList());
        return new PageableResponse<>(request, totalElements, getNewsResponses);
    }

    private GetNewsResponse convertNewsToGetNewsResponse(News news) {
        UserInfo userInfo = userInfoService.getUserInfo(news.getCreatedBy());
        String thumbnailURL = news.getThumbnail() == null ? null : s3BucketStorageService.getPublicURL(news.getThumbnail().getFileKey());
        return GetNewsResponse.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .author(userInfo == null ? null : userInfo.getFullName())
                .thumbnail(thumbnailURL)
                .createdDate(news.getCreatedDate())
                .views(news.getViews())
                .comments(news.getComments().size())
                .build();
    }
}
