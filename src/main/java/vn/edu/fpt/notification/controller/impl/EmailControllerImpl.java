package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.EmailController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.SortableRequest;
import vn.edu.fpt.notification.dto.request.email.*;
import vn.edu.fpt.notification.dto.response.email.CreateEmailTemplateResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailHistoryResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailTemplateResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.EmailService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:13
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class EmailControllerImpl implements EmailController {

    private final EmailService emailService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<Object>> sendEmail(String templateId, SendEmailRequest request) {
        emailService.sendEmail(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetEmailHistoryResponse>>> getEmailHistory(String historyId,
                                                                                                      String templateId,
                                                                                                      String templateName,
                                                                                                      String templateNameSortBy,
                                                                                                      String sendTo,
                                                                                                      String sendToSortBy,
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
        if(Objects.nonNull(sendToSortBy)){
            sortableRequests.add(new SortableRequest("send_to", sendToSortBy));
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
        GetEmailHistoryRequest request = GetEmailHistoryRequest.builder()
                .historyId(historyId)
                .templateId(templateId)
                .templateName(templateName)
                .sendTo(sendTo)
                .status(status)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .page(page)
                .size(size)
                .sortBy(sortableRequests)
                .build();
        return responseFactory.response(emailService.getEmailHistory(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateEmailTemplateResponse>> createEmailTemplate(CreateEmailTemplateRequest request) {
        return responseFactory.response(emailService.createEmailTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateEmailTemplate(String templateId, UpdateEmailTemplateRequest request) {
        emailService.updateEmailTemplate(templateId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteEmailTemplate(String templateId) {
        emailService.deleteEmailTemplate(templateId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetEmailTemplateResponse>>> getEmailTemplate(String templateId,
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
            sortableRequests.add(new SortableRequest("_id", messageSortBy));
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
        GetEmailTemplateRequest request = GetEmailTemplateRequest.builder()
                .templateId(templateId)
                .templateName(templateName)
                .message(message)
                .createdBy(createdBy)
                .createdDateTo(createdDateTo)
                .createdDateFrom(createdDateFrom)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateTo(lastModifiedDateTo)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .sortBy(sortableRequests)
                .page(page)
                .size(size)
                .build();
        return responseFactory.response(emailService.getEmailTemplate(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> downloadEmailTemplateAttachFile(String fileKey, HttpServletResponse response) {
        emailService.downloadTemplateAttachFile(fileKey, response);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }
}
