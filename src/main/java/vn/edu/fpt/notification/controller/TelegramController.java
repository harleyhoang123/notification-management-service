package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.telegram.CreateTelegramTemplateRequest;
import vn.edu.fpt.notification.dto.request.telegram.SendTelegramRequest;
import vn.edu.fpt.notification.dto.request.telegram.UpdateTelegramTemplateRequest;
import vn.edu.fpt.notification.dto.response.telegram.CreateTelegramTemplateResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramHistoryResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramTemplateResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:13
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/private/api/v1/telegrams")
public interface TelegramController {

    @PostMapping("/telegram/{template-id}")
    ResponseEntity<GeneralResponse<Object>> sendNotificationToTelegram(
            @PathVariable("template-id") String templateId,
            @RequestBody SendTelegramRequest request);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetTelegramHistoryResponse>>> getTelegramHistory(
            @RequestParam(name = "history-id", required = false) String historyId,
            @RequestParam(name = "template-id", required = false) String templateId,
            @RequestParam(name = "template-name", required = false) String templateName,
            @RequestParam(name = "template-name-sort-by", required = false) String templateNameSortBy,
            @RequestParam(name = "channel-name", required = false) String channelName,
            @RequestParam(name = "channel-name-sort-by", required = false) String channelNameSortBy,
            @RequestParam(name = "message", required = false) String message,
            @RequestParam(name = "message-sort-by", required = false) String messageSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-by-sort-by", required = false) String createdBySortBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    );

    @GetMapping("/templates")
    ResponseEntity<GeneralResponse<PageableResponse<GetTelegramTemplateResponse>>> getTelegramTemplate(
        @RequestParam(name = "template-id", required = false) String templateId,
        @RequestParam(name = "template-name", required = false) String templateName,
        @RequestParam(name = "template-name-sort-by", required = false) String templateNameSortBy,
        @RequestParam(name = "channel-name", required = false) String channelName,
        @RequestParam(name = "channel-name-sort-by", required = false) String channelNameSortBy,
        @RequestParam(name = "message", required = false) String message,
        @RequestParam(name = "message-sort-by", required = false) String messageSortBy,
        @RequestParam(name = "created-by", required = false) String createdBy,
        @RequestParam(name = "created-by-sort-by", required = false) String createdBySortBy,
        @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
        @RequestParam(name = "created-date-to", required = false) String createdDateTo,
        @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
        @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
        @RequestParam(name = "last-modified-by-sort-by", required = false) String lastModifiedBySortBy,
        @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
        @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
        @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
        @RequestParam(name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size

    );

    @PostMapping("/templates/template")
    ResponseEntity<GeneralResponse<CreateTelegramTemplateResponse>> createTelegramTemplate(@RequestBody CreateTelegramTemplateRequest request);

    @PutMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> updateNotificationTelegramTemplate(
            @PathVariable("template-id") String templateId,
            @RequestBody UpdateTelegramTemplateRequest request
    );

    @DeleteMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> deleteTelegramTemplate(@PathVariable("template-id") String templateId);

}
