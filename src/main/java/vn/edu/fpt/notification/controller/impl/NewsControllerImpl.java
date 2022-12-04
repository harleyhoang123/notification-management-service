package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.controller.NewsController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.NewsService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NewsControllerImpl implements NewsController {

    private final ResponseFactory responseFactory;
    private final NewsService newsService;
    @Override
    public ResponseEntity<GeneralResponse<Object>> createNews(CreateNewsRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateNews(String newsId, UpdateNewsRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteNews(String newsId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetNewsDetailResponse>> getNewsDetail(String newsId) {
        return null;
    }
}
