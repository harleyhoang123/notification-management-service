package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;

public interface NewsService {

    void createNews(CreateNewsRequest request);

    void updateNews(String newsId, UpdateNewsRequest request);

    void deleteNews(String newsId);

    GetNewsDetailResponse getNewsDetailResponse(String newsId);
}
