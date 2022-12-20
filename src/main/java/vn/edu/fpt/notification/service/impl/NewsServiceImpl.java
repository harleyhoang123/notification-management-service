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
import vn.edu.fpt.notification.dto.response.comment.GetCommentDetailResponse;
import vn.edu.fpt.notification.dto.response.news.CreateNewsResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsResponse;
import vn.edu.fpt.notification.dto.response.news._GetAttachmentResponse;
import vn.edu.fpt.notification.entity.Comment;
import vn.edu.fpt.notification.entity.News;
import vn.edu.fpt.notification.entity._Attachment;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.AttachmentRepository;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.CommentRepository;
import vn.edu.fpt.notification.repository.NewsRepository;
import vn.edu.fpt.notification.service.CommentService;
import vn.edu.fpt.notification.service.NewsService;
import vn.edu.fpt.notification.service.S3BucketStorageService;
import vn.edu.fpt.notification.service.UserInfoService;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Override
    public CreateNewsResponse createNews(CreateNewsRequest request) {
        if (newsRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News title already exist");
        }
        String fileKey = s3BucketStorageService.uploadFile(request.getThumbnail());
        _Attachment attachment = _Attachment.builder()
                .fileKey(fileKey)
                .build();
        try {
            attachment = attachmentRepository.save(attachment);
        } catch (Exception ex) {
            throw new BusinessException("Can't save attachment to database: " + ex.getMessage());
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
        if (Objects.nonNull(request.getThumbnail())) {
            if (Objects.nonNull(news.getThumbnail())) {
//                s3BucketStorageService.deleteFile(news.getThumbnail().getFileKey());
//                log.info("Delete file in S3 success: {}", news.getThumbnail().getFileKey());
                try {
                    attachmentRepository.delete(news.getThumbnail());
                    log.info("Delete attachment success");
                }catch (Exception ex){
                    throw new BusinessException("Can't delete attachment in database: "+ ex.getMessage());
                }
            }
            String fileKey = s3BucketStorageService.uploadFile(request.getThumbnail());
            _Attachment attachment = _Attachment.builder()
                    .fileKey(fileKey)
                    .build();
            try {
                attachment = attachmentRepository.save(attachment);
            }catch (Exception ex){
                throw new BusinessException("Can't save attachment to database"+ ex.getMessage());
            }
            news.setThumbnail(attachment);
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
        List<Comment> comments = news.getComments();
        if (!comments.isEmpty()) {
            for (Comment c: comments) {
                this.deleteCommentFromNews(newsId, c.getCommentId());
            }
        }

        try {
            newsRepository.delete(news);
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
        List<GetCommentDetailResponse> commentDetailResponses = news.getComments().stream().map(this::convertCommentToGetCommentResponse).collect(Collectors.toList());

        GetNewsDetailResponse getNewsDetailResponse = GetNewsDetailResponse.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .content(news.getContent())
                .views(news.getViews())
                .comments(commentDetailResponses)
                .createdBy(userInfoService.getUserInfo(news.getCreatedBy()))
                .createdDate(news.getCreatedDate())
                .lastModifiedBy(userInfoService.getUserInfo(news.getLastModifiedBy()))
                .lastModifiedDate(news.getLastModifiedDate())
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

    private GetCommentDetailResponse convertCommentToGetCommentResponse(Comment comment) {
        List<Comment> comments = comment.getComments();
        List<GetCommentDetailResponse> getCommentDetailResponses;
        if (!comments.isEmpty()) {
            getCommentDetailResponses = comments.stream().map(this::convertCommentToGetCommentResponse).collect(Collectors.toList());
        } else {
            getCommentDetailResponses = new ArrayList<>();
        }
        return GetCommentDetailResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .comments(getCommentDetailResponses)
                .createdBy(userInfoService.getUserInfo(comment.getCreatedBy()))
                .createdDate(comment.getCreatedDate())
                .lastModifiedBy(userInfoService.getUserInfo(comment.getLastModifiedBy()))
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();
    }

    @Override
    public PageableResponse<GetNewsResponse> getNews(GetNewsRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(request.getTitle()));
        }
        query.with(Sort.by(Sort.Direction.DESC, "created_date"));

        Long totalElements = mongoTemplate.count(query, News.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);

        List<News> news = mongoTemplate.find(query, News.class);

        List<GetNewsResponse> getNewsResponses = news.stream().map(this::convertNewsToGetNewsResponse).collect(Collectors.toList());
        return new PageableResponse<>(request, totalElements, getNewsResponses);
    }

    @Override
    public void deleteCommentFromNews(String newsId, String commentId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News ID not exist"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Comment ID not exist"));
        List<Comment> list = news.getComments();
        list.removeIf(v-> v.getCommentId().equals(commentId));
        news.setComments(list);
        List<Comment> subComments = comment.getComments();
        if (!subComments.isEmpty()){
            for (Comment c: subComments) {
                commentService.deleteSubCommentFromComment(commentId, c.getCommentId());
            }
        }
        try{
            commentRepository.delete(comment);
            log.info("Delete comment from comment success");
        } catch (Exception ex){
            throw new BusinessException("Can't delete comment from comment to database" + ex.getMessage());
        }
        try {
            newsRepository.save(news);
            log.info("save new success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save new to database: " + ex.getMessage());
        }
    }

    private GetNewsResponse convertNewsToGetNewsResponse(News news) {
        UserInfo userInfo = userInfoService.getUserInfo(news.getCreatedBy());
        return GetNewsResponse.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .author(userInfo == null ? null : userInfo.getFullName())
                .thumbnail(_GetAttachmentResponse.builder()
                        .attachmentId(news.getThumbnail().getAttachmentId())
                        .URL(s3BucketStorageService.getPublicURL(news.getThumbnail().getFileKey()))
                        .build())
                .createdDate(news.getCreatedDate())
                .views(news.getViews())
                .comments(news.getComments().size())
                .build();
    }
}
