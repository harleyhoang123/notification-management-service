package vn.edu.fpt.notification.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.display_message.CreateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.GetDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.UpdateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.response.display_message.CreateDisplayMessageResponse;
import vn.edu.fpt.notification.dto.response.display_message.GetDisplayMessageResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:35
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface DisplayMessageService {

    String getDisplayMessage(String code);

    String getDisplayMessage(String code, String language);

    CreateDisplayMessageResponse createDisplayMessage(CreateDisplayMessageRequest request);

    void updateDisplayMessage(String displayMessageId, UpdateDisplayMessageRequest request);

    void deleteDisplayMessage(String displayMessageId);

    PageableResponse<GetDisplayMessageResponse> getDisplayMessageByCondition(GetDisplayMessageRequest request);

}
