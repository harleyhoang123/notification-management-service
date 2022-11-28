package vn.edu.fpt.notification.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.email.CreateEmailTemplateRequest;
import vn.edu.fpt.notification.dto.request.email.SendEmailRequest;
import vn.edu.fpt.notification.dto.request.email.UpdateEmailTemplateRequest;
import vn.edu.fpt.notification.dto.response.email.CreateEmailTemplateResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailHistoryResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailTemplateResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:13
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/private/api/v1/emails")
public interface EmailController {
    @PostMapping("/{template-id}/email")
    ResponseEntity<GeneralResponse<Object>> sendEmail(
            @PathVariable("template-id") String templateId,
            @RequestBody SendEmailRequest request);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetEmailHistoryResponse>>> getEmailHistory(
            @RequestParam(name = "history_id", required = false) String historyId,
            @RequestParam(name = "template-id", required = false) String templateId,
            @RequestParam(name = "template-name", required = false) String templateName,
            @RequestParam(name = "template-name-sort-by", required = false) String templateNameSortBy,
            @RequestParam(name = "send-to", required = false) String sendTo,
            @RequestParam(name = "send-to-sort-by", required = false) String sendToSortBy,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "status-sort-by", required = false) String statusSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-by-sort-by", required = false) String createdBySortBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    );

    @PostMapping(value = "/templates/template", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<GeneralResponse<CreateEmailTemplateResponse>> createEmailTemplate(@ModelAttribute CreateEmailTemplateRequest request);

    @PutMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> updateEmailTemplate(
            @PathVariable("template-id") String templateId,
            @RequestBody UpdateEmailTemplateRequest request
    );

    @DeleteMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> deleteEmailTemplate(@PathVariable("template-id") String templateId);

    @GetMapping("/templates")
    ResponseEntity<GeneralResponse<PageableResponse<GetEmailTemplateResponse>>> getEmailTemplate(
            @RequestParam(name = "template-id", required = false) String templateId,
            @RequestParam(name = "template-name", required = false) String templateName,
            @RequestParam(name = "template-name-sort-by", required = false) String templateNameSortBy,
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



    @GetMapping("/templates/{file-key}/file")
    ResponseEntity<GeneralResponse<Object>> downloadEmailTemplateAttachFile(
            @PathVariable("file-key") String fileKey,
            HttpServletResponse response
    );
}
