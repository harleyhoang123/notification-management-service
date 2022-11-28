package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.config.security.annotation.IsAdmin;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.display_message.CreateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.UpdateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.response.display_message.CreateDisplayMessageResponse;
import vn.edu.fpt.notification.dto.response.display_message.GetDisplayMessageResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 01/09/2022 - 00:09
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/private/api/v1/display-messages")
public interface DisplayMessageController {

    @PostMapping("/display-message")
    ResponseEntity<GeneralResponse<CreateDisplayMessageResponse>> createDisplayMessage(@RequestBody CreateDisplayMessageRequest request);

    @PutMapping("/{display-message-id}")
    ResponseEntity<GeneralResponse<Object>> updateDisplayMessage(
            @PathVariable("display-message-id") String displayMessageId,
            @RequestBody UpdateDisplayMessageRequest request
    );

    @DeleteMapping("/{display-message-id}")
    @IsAdmin
    ResponseEntity<GeneralResponse<Object>> deleteDisplayMessageById(@PathVariable("display-message-id") String displayMessageId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetDisplayMessageResponse>>> getDisplayMessageByCondition(
            @RequestParam(name = "display-message-id", required = false) String displayMessageId,
            @RequestParam(name = "display-message-id-sort-by", required = false) String displayMessageIdSortBy,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "code-sort-by", required = false) String codeSortBy,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "language-sort-by", required = false) String languageSortBy,
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
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy
    );
}
