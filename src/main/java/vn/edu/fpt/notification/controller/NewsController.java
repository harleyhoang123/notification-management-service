package vn.edu.fpt.notification.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.comment._CreateCommentRequest;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.comment._CreateCommentResponse;
import vn.edu.fpt.notification.dto.response.news.CreateNewsResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsResponse;

@RequestMapping("${app.application-context}/public/api/v1/news")
public interface NewsController {

    @PostMapping(value = "/news")
    ResponseEntity<GeneralResponse<CreateNewsResponse>> createNews(@RequestBody CreateNewsRequest request);
    @PutMapping(value = "/{news-id}")
    ResponseEntity<GeneralResponse<Object>> updateNews(@PathVariable("news-id") String newsId, @RequestBody UpdateNewsRequest request);
    @DeleteMapping("/{news-id}")
    ResponseEntity<GeneralResponse<Object>> deleteNews(@PathVariable(name = "news-id") String newsId);
    @GetMapping("/{news-id}")
    ResponseEntity<GeneralResponse<GetNewsDetailResponse>> getNewsDetail(@PathVariable(name = "news-id") String newsId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetNewsResponse>>> getNews(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
            );

    @PostMapping("/{news-id}/comment")
    ResponseEntity<GeneralResponse<_CreateCommentResponse>> addCommentToNews(@PathVariable("news-id") String newsId, @RequestBody _CreateCommentRequest request);
    @DeleteMapping("/{news-id}/comment")
    ResponseEntity<GeneralResponse<Object>> deleteCommentToNews(String newsId, String commentId);
}
