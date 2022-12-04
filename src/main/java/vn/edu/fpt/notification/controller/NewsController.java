package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;

@RequestMapping("${app.application-context}/public/api/v1/news")
public interface NewsController {
    @PostMapping("/news")
    ResponseEntity<GeneralResponse<Object>> createNews(@RequestBody CreateNewsRequest request);
    @PutMapping("/news-id")
    ResponseEntity<GeneralResponse<Object>> updateNews(@PathVariable("news-id") String newsId, @RequestBody UpdateNewsRequest request);
    @DeleteMapping("/news-id")
    ResponseEntity<GeneralResponse<Object>> deleteNews(@PathVariable(name = "news-id") String newsId);
    @GetMapping("/news-id")
    ResponseEntity<GeneralResponse<GetNewsDetailResponse>> getNewsDetail(@PathVariable(name = "new-id") String newsId);

}
