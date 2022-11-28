package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.telegram.*;
import vn.edu.fpt.notification.dto.response.telegram.CreateTelegramTemplateResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramHistoryResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramTemplateResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 19:28
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface TelegramService {

    void sendNotification(String templateId, SendTelegramRequest request);

    void sendNotification(String value);

    CreateTelegramTemplateResponse createTelegramTemplate(CreateTelegramTemplateRequest request);

    void updateTelegramTemplate(String templateId, UpdateTelegramTemplateRequest request);

    void deleteTelegramTemplate(String templateId);

    PageableResponse<GetTelegramTemplateResponse> getTelegramTemplate(GetTelegramTemplateRequest request);

    PageableResponse<GetTelegramHistoryResponse> getTelegramHistory(GetTelegramHistoryRequest request);
}
