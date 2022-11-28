package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.SmsController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.SortableRequest;
import vn.edu.fpt.notification.dto.request.sms.*;
import vn.edu.fpt.notification.dto.response.sms.CreateSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsHistoryResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.UpdateSmsTemplateResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.SmsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:21
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class SmsControllerImpl implements SmsController {

    private final SmsService smsService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<Object>> sendSms(String templateId, SendSmsRequest request) {
        smsService.sendSms(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> registerPhoneNumberReceivedSms(RegisterPhoneNumberReceivedSmsRequest request) {
        smsService.registerPhoneNumberReceivedSms(request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> verifyPhoneNumber(VerifyPhoneNumberOtpRequest request) {
        smsService.verifyPhoneNumberCode(request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateSmsTemplateResponse>> createSmsTemplate(CreateSmsTemplateRequest request) {
        return responseFactory.response(smsService.createSmsTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateSmsTemplate(String templateId, UpdateSmsTemplateRequest request) {
        smsService.updateSmsTemplate(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteSmsTemplate(String templateId) {
        smsService.deleteSmsTemplate(templateId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetSmsTemplateResponse>>> getSmsTemplate(
            String templateId,
            String templateName,
            String templateNameSortBy,
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
        if(Objects.nonNull(templateNameSortBy)){
            sortableRequests.add(new SortableRequest("template_name", templateNameSortBy));
        }
        if(Objects.nonNull(messageSortBy)){
            sortableRequests.add(new SortableRequest("message", messageSortBy));
        }
        if(Objects.nonNull(createdBySortBy)){
            sortableRequests.add(new SortableRequest("created_by", createdBySortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedBySortBy)){
            sortableRequests.add(new SortableRequest("last_modified_by", lastModifiedBySortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)){
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        GetSmsTemplateRequest request = GetSmsTemplateRequest.builder()
                .templateId(templateId)
                .templateName(templateName)
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
                .build() ;
        return responseFactory.response(smsService.getSmsTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetSmsHistoryResponse>>> getSmsHistory(String historyId,
                                                                                                  String templateId,
                                                                                                  String templateName,
                                                                                                  String templateNameSortBy,
                                                                                                  String message,
                                                                                                  String messageSortBy,
                                                                                                  String status,
                                                                                                  String statusSortBy,
                                                                                                  String createdBy,
                                                                                                  String createdBySortBy,
                                                                                                  String createdDateFrom,
                                                                                                  String createdDateTo,
                                                                                                  String createdDateSortBy,
                                                                                                  Integer page,
                                                                                                  Integer size) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(templateNameSortBy)){
            sortableRequests.add(new SortableRequest("template_name", templateNameSortBy));
        }
        if(Objects.nonNull(messageSortBy)){
            sortableRequests.add(new SortableRequest("message", messageSortBy));
        }
        if(Objects.nonNull(statusSortBy)){
            sortableRequests.add(new SortableRequest("status", statusSortBy));
        }
        if(Objects.nonNull(createdBySortBy)){
            sortableRequests.add(new SortableRequest("created_by", createdBySortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        GetSmsHistoryRequest request = GetSmsHistoryRequest.builder()
                .historyId(historyId)
                .templateId(templateId)
                .templateName(templateName)
                .message(message)
                .status(status)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .page(page)
                .size(size)
                .sortBy(sortableRequests)
                .build();
        return responseFactory.response(smsService.getSmsHistory(request));
    }
}
