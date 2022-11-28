package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.TelegramController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.SortableRequest;
import vn.edu.fpt.notification.dto.request.telegram.*;
import vn.edu.fpt.notification.dto.response.telegram.CreateTelegramTemplateResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramHistoryResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramTemplateResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.TelegramService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 19:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class TelegramControllerImpl implements TelegramController {

    private final TelegramService telegramService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<Object>> sendNotificationToTelegram(String templateId, SendTelegramRequest request) {
        telegramService.sendNotification(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetTelegramHistoryResponse>>> getTelegramHistory(String historyId,
                                                                                                            String templateId,
                                                                                                            String templateName,
                                                                                                            String templateNameSortBy,
                                                                                                            String channelName,
                                                                                                            String channelNameSortBy,
                                                                                                            String message,
                                                                                                            String messageSortBy,
                                                                                                            String createdBy,
                                                                                                            String createdBySortBy,
                                                                                                            String createdDateFrom,
                                                                                                            String createdDateTo,
                                                                                                            String createdDateSortBy,
                                                                                                            Integer page,
                                                                                                            Integer size) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if (Objects.nonNull(templateNameSortBy)) {
            sortableRequests.add(new SortableRequest("template_name", templateNameSortBy));
        }
        if (Objects.nonNull(channelNameSortBy)) {
            sortableRequests.add(new SortableRequest("channel_name", channelNameSortBy));
        }
        if (Objects.nonNull(messageSortBy)) {
            sortableRequests.add(new SortableRequest("message", messageSortBy));
        }
        if (Objects.nonNull(createdBySortBy)) {
            sortableRequests.add(new SortableRequest("created_by", createdBySortBy));
        }
        if (Objects.nonNull(createdDateSortBy)) {
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        GetTelegramHistoryRequest request = GetTelegramHistoryRequest.builder()
                .historyId(historyId)
                .templateId(templateId)
                .templateName(templateName)
                .channelName(channelName)
                .message(message)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .sortBy(sortableRequests)
                .page(page)
                .size(size)
                .build();
        return responseFactory.response(telegramService.getTelegramHistory(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetTelegramTemplateResponse>>> getTelegramTemplate(String templateId,
                                                                                                              String templateName,
                                                                                                              String templateNameSortBy,
                                                                                                              String channelName,
                                                                                                              String channelNameSortBy,
                                                                                                              String message,
                                                                                                              String messageSortBy,
                                                                                                              String createdBy,
                                                                                                              String createdBySortBy,
                                                                                                              String createdDateFrom,
                                                                                                              String createdDateTo,
                                                                                                              String createdDateSortBy,
                                                                                                              String lastModifiedBy,
                                                                                                              String lastModifiedBySortBy,
                                                                                                              String lastModifiedDateFrom,
                                                                                                              String lastModifiedDateTo,
                                                                                                              String lastModifiedDateSortBy,
                                                                                                              Integer page,
                                                                                                              Integer size) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if (Objects.nonNull(templateNameSortBy)) {
            sortableRequests.add(new SortableRequest("template_name", templateNameSortBy));
        }
        if (Objects.nonNull(channelNameSortBy)) {
            sortableRequests.add(new SortableRequest("channel_name", channelNameSortBy));
        }
        if (Objects.nonNull(messageSortBy)) {
            sortableRequests.add(new SortableRequest("message", messageSortBy));
        }
        if (Objects.nonNull(createdBySortBy)) {
            sortableRequests.add(new SortableRequest("created_by", createdBySortBy));
        }
        if (Objects.nonNull(createdDateSortBy)) {
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if (Objects.nonNull(lastModifiedBySortBy)) {
            sortableRequests.add(new SortableRequest("last_modified_by", lastModifiedBySortBy));
        }
        if (Objects.nonNull(lastModifiedDateSortBy)) {
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        GetTelegramTemplateRequest request = GetTelegramTemplateRequest.builder()
                .templateId(templateId)
                .templateName(templateName)
                .channelName(channelName)
                .message(message)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateTo(lastModifiedDateTo)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .sortBy(sortableRequests)
                .page(page)
                .size(size)
                .build();
        return responseFactory.response(telegramService.getTelegramTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateTelegramTemplateResponse>> createTelegramTemplate(CreateTelegramTemplateRequest request) {
        return responseFactory.response(telegramService.createTelegramTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateNotificationTelegramTemplate(String templateId, UpdateTelegramTemplateRequest request) {
        telegramService.updateTelegramTemplate(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteTelegramTemplate(String templateId) {
        telegramService.deleteTelegramTemplate(templateId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }
}
