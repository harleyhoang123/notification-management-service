package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.DisplayMessageController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.SortableRequest;
import vn.edu.fpt.notification.dto.request.display_message.CreateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.GetDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.UpdateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.response.display_message.CreateDisplayMessageResponse;
import vn.edu.fpt.notification.dto.response.display_message.GetDisplayMessageResponse;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.DisplayMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 01/09/2022 - 00:09
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@Slf4j
@RequiredArgsConstructor
public class DisplayMessageControllerImpl implements DisplayMessageController {

    private final DisplayMessageService displayMessageService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<CreateDisplayMessageResponse>> createDisplayMessage(CreateDisplayMessageRequest request) {
        return responseFactory.response(displayMessageService.createDisplayMessage(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateDisplayMessage(String displayMessageId, UpdateDisplayMessageRequest request) {
        displayMessageService.updateDisplayMessage(displayMessageId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteDisplayMessageById(String displayMessageId) {
        displayMessageService.deleteDisplayMessage(displayMessageId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetDisplayMessageResponse>>> getDisplayMessageByCondition(
            String displayMessageId,
            String displayMessageIdSortBy,
            String code,
            String codeSortBy,
            String language,
            String languageSortBy,
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
            String lastModifiedDateSortBy) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(displayMessageIdSortBy)){
            sortableRequests.add(new SortableRequest("_id", displayMessageIdSortBy));
        }
        if(Objects.nonNull(codeSortBy)){
            sortableRequests.add(new SortableRequest("code", codeSortBy));
        }
        if(Objects.nonNull(languageSortBy)){
            sortableRequests.add(new SortableRequest("language", languageSortBy));
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

        GetDisplayMessageRequest request = GetDisplayMessageRequest.builder()
                .displayMessageId(displayMessageId)
                .code(code)
                .language(language)
                .message(message)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .lastModifiedDateTo(lastModifiedDateTo)
                .sortBy(sortableRequests)
                .build();
        return responseFactory.response(displayMessageService.getDisplayMessageByCondition(request));
    }

}
