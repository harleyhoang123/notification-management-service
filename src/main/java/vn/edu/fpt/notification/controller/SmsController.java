package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.sms.*;
import vn.edu.fpt.notification.dto.response.sms.CreateSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsHistoryResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.UpdateSmsTemplateResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:21
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/private/api/v1/sms")
public interface SmsController {

    @PostMapping("/{template-id}")
    ResponseEntity<GeneralResponse<Object>> sendSms(
            @PathVariable("template-id") String templateId,
            @RequestBody SendSmsRequest request);

    @PostMapping("/phone-number")
    ResponseEntity<GeneralResponse<Object>> registerPhoneNumberReceivedSms(@RequestBody RegisterPhoneNumberReceivedSmsRequest request);

    @PostMapping("/phone-number/otp")
    ResponseEntity<GeneralResponse<Object>> verifyPhoneNumber(@RequestBody VerifyPhoneNumberOtpRequest request);

    @PostMapping("/templates")
    ResponseEntity<GeneralResponse<CreateSmsTemplateResponse>> createSmsTemplate(@RequestBody CreateSmsTemplateRequest request);

    @PutMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> updateSmsTemplate(
            @PathVariable("template-id") String templateId,
            @RequestBody UpdateSmsTemplateRequest request
    );

    @DeleteMapping("/templates/{template-id}")
    ResponseEntity<GeneralResponse<Object>> deleteSmsTemplate(@PathVariable("template-id") String templateId);

    @GetMapping("/templates")
    ResponseEntity<GeneralResponse<PageableResponse<GetSmsTemplateResponse>>> getSmsTemplate(
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

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetSmsHistoryResponse>>> getSmsHistory(
            @RequestParam(name = "history-id", required = false) String historyId,
            @RequestParam(name = "template-id", required = false) String templateId,
            @RequestParam(name = "template-name", required = false) String templateName,
            @RequestParam(name = "template-name-sort-by", required = false) String templateNameSortBy,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "status-sort-by", required = false) String statusSortBy,
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

}
