package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.NewsController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.comment._CreateCommentRequest;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.GetNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.comment._CreateCommentResponse;
import vn.edu.fpt.notification.dto.response.news.CreateNewsResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.CommentService;
import vn.edu.fpt.notification.service.NewsService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {

    private final ResponseFactory responseFactory;
    private final NewsService newsService;
    private final CommentService commentService;


    @Override
    public ResponseEntity<GeneralResponse<CreateNewsResponse>> createNews(CreateNewsRequest request) {
        return responseFactory.response(newsService.createNews(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateNews(String newsId, UpdateNewsRequest request) {
        newsService.updateNews(newsId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteNews(String newsId) {
        newsService.deleteNews(newsId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<GetNewsDetailResponse>> getNewsDetail(String newsId) {
        return responseFactory.response(newsService.getNewsDetailResponse(newsId));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetNewsResponse>>> getNews(String title, Integer page, Integer size) {
        GetNewsRequest request = GetNewsRequest.builder()
                .title(title)
                .page(page)
                .size(size)
                .build();
        return responseFactory.response(newsService.getNews(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<_CreateCommentResponse>> addCommentToNews(String newsId, _CreateCommentRequest request) {
        return responseFactory.response(commentService.addCommentToNews(newsId, request));
    }


}
