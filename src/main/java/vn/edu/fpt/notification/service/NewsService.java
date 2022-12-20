package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.news.CreateNewsRequest;
import vn.edu.fpt.notification.dto.request.news.GetNewsRequest;
import vn.edu.fpt.notification.dto.request.news.UpdateNewsRequest;
import vn.edu.fpt.notification.dto.response.news.CreateNewsResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsDetailResponse;
import vn.edu.fpt.notification.dto.response.news.GetNewsResponse;

public interface NewsService {

    CreateNewsResponse createNews(CreateNewsRequest request);

    void updateNews(String newsId, UpdateNewsRequest request);

    void deleteNews(String newsId);

    GetNewsDetailResponse getNewsDetailResponse(String newsId);

    PageableResponse<GetNewsResponse> getNews(GetNewsRequest request);

    void deleteCommentFromNews(String newsId, String commentId);
}
