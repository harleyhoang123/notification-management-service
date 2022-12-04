package vn.edu.fpt.notification.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.config.security.annotation.IsAdmin;
import vn.edu.fpt.notification.constant.AppConstant;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.event.SendTelegramEvent;
import vn.edu.fpt.notification.dto.request.telegram.*;
import vn.edu.fpt.notification.dto.response.telegram.CreateTelegramTemplateResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramHistoryResponse;
import vn.edu.fpt.notification.dto.response.telegram.GetTelegramTemplateResponse;
import vn.edu.fpt.notification.entity.EmailTemplate;
import vn.edu.fpt.notification.entity.TelegramHistory;
import vn.edu.fpt.notification.entity.TelegramTemplate;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.TelegramHistoryRepository;
import vn.edu.fpt.notification.repository.TelegramTemplateRepository;
import vn.edu.fpt.notification.service.TelegramService;
import vn.edu.fpt.notification.utils.ParamUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 19:29
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

//    private final TelegramBotService telegramBotService;
    private final TelegramTemplateRepository telegramTemplateRepository;
    private final TelegramHistoryRepository telegramHistoryRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;


    public void sendNotification(String templateId, SendTelegramRequest request) {
//        TelegramTemplate telegramTemplate = telegramTemplateRepository.findById(templateId)
//                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Telegram template id not exist!"));
//        String message = ParamUtils.replaceParams(telegramTemplate.getMessage(), request.getParams());
//        String status;
//        try {
//            telegramBotService.sendMessage(telegramTemplate.getChatId(), message);
//            status = AppConstant.SUCCESS;
//        } catch (Exception ex) {
//            status = AppConstant.FAILED;
//        }
//
//        telegramHistoryRepository.save(TelegramHistory.builder()
//                .template(telegramTemplate)
//                .params(request.getParams())
//                .message(message)
//                .status(status)
//                .build());
    }

    @Override
    public void sendNotification(String value) {
        try {
            SendTelegramEvent sendTelegramEvent = objectMapper.readValue(value, SendTelegramEvent.class);
            this.sendNotification(sendTelegramEvent.getTemplateId(), SendTelegramRequest.builder()
                            .params(sendTelegramEvent.getParams())
                    .build());
        } catch (JsonProcessingException ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't convert value from flab.send_telegram topics: "+ ex.getMessage());
        }
    }

    @Override
    public CreateTelegramTemplateResponse createTelegramTemplate(CreateTelegramTemplateRequest request) {
        TelegramTemplate telegramTemplate = TelegramTemplate.builder()
                .templateName(request.getTemplateName())
                .message(request.getMessage())
                .params(request.getParams())
                .chatId(request.getChatId())
                .build();
        try {
            telegramTemplate = telegramTemplateRepository.save(telegramTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't save telegram template to database: " + ex.getMessage());
        }
        return CreateTelegramTemplateResponse.builder()
                .templateId(telegramTemplate.getTelegramTemplateId())
                .build();
    }

    @Override
    public void updateTelegramTemplate(String templateId, UpdateTelegramTemplateRequest request) {
        TelegramTemplate telegramTemplate = telegramTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Telegram template id not exist"));

        if (Objects.nonNull(request.getTemplateName())) {
            telegramTemplate.setTemplateName(request.getTemplateName());
        }
        if (Objects.nonNull(request.getMessage())) {
            telegramTemplate.setMessage(request.getMessage());
        }
        if (Objects.nonNull(request.getChatId())) {
            telegramTemplate.setChatId(request.getChatId());
        }
        if (Objects.nonNull(request.getParams())) {
            telegramTemplate.setParams(request.getParams());
        }

        try {
            telegramTemplateRepository.save(telegramTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update telegram template in database: " + ex.getMessage());
        }
    }

    @Override
    @IsAdmin
    public void deleteTelegramTemplate(String templateId) {
        TelegramTemplate telegramTemplate = telegramTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Telegram template id not exist"));
        try {
            telegramTemplateRepository.delete(telegramTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't delete telegram template in database: " + ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetTelegramTemplateResponse> getTelegramTemplate(GetTelegramTemplateRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getTemplateId())) {
            query.addCriteria(Criteria.where("_id").regex(request.getTemplateId()));
        }
        if (Objects.nonNull(request.getTemplateName())) {
            query.addCriteria(Criteria.where("template_name").regex(request.getTemplateName()));
        }
        if (Objects.nonNull(request.getMessage())) {
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        if (Objects.nonNull(request.getChatId())) {
            query.addCriteria(Criteria.where("chat_id").regex(request.getChatId()));
        }

        BaseMongoRepository.addCriteriaWithAuditable(query, request);
        Long totalElements = mongoTemplate.count(query, EmailTemplate.class);
        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        try {
            List<TelegramTemplate> telegramTemplates = mongoTemplate.find(query, TelegramTemplate.class);
            List<GetTelegramTemplateResponse> getTelegramTemplateResponses = telegramTemplates.stream().map(this::getTelegramTemplateResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, getTelegramTemplateResponses);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find telegram template in db: " + ex.getMessage());
        }
    }

    private GetTelegramTemplateResponse getTelegramTemplateResponse(TelegramTemplate telegramTemplate) {
        return GetTelegramTemplateResponse.builder()
                .templateId(telegramTemplate.getTelegramTemplateId())
                .templateName(telegramTemplate.getTemplateName())
                .channelId(telegramTemplate.getChatId())
                .message(telegramTemplate.getMessage())
                .params(telegramTemplate.getParams())
                .createdBy(telegramTemplate.getCreatedBy())
                .createdDate(telegramTemplate.getCreatedDate())
                .lastModifiedBy(telegramTemplate.getLastModifiedBy())
                .lastModifiedDate(telegramTemplate.getLastModifiedDate())
                .build();
    }

    @Override
    public PageableResponse<GetTelegramHistoryResponse> getTelegramHistory(GetTelegramHistoryRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getHistoryId())) {
            query.addCriteria(Criteria.where("_id").regex(request.getHistoryId()));
        }
        if (Objects.nonNull(request.getTemplateId())) {
            query.addCriteria(Criteria.where("template.$_id").regex(request.getTemplateId()));
        }
        if (Objects.nonNull(request.getTemplateName())) {
            query.addCriteria(Criteria.where("template.$template_name").regex(request.getTemplateName()));
        }
        if (Objects.nonNull(request.getMessage())) {
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        if (Objects.nonNull(request.getStatus())) {
            query.addCriteria(Criteria.where("status").regex(request.getStatus()));
        }
        if (Objects.nonNull(request.getCreatedBy())) {
            query.addCriteria(Criteria.where("created_by").regex(request.getCreatedBy()));
        }
        query.addCriteria(Criteria.where("created_date").gte(request.getCreatedDateFrom()).lte(request.getCreatedDateTo()));
        Long totalElements = mongoTemplate.count(query, TelegramHistory.class);
        try {
            List<TelegramHistory> telegramHistories = mongoTemplate.find(query, TelegramHistory.class);
            List<GetTelegramHistoryResponse> telegramHistoryResponses = telegramHistories.stream().map(this::convertToTelegramHistoryResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, telegramHistoryResponses);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find telegram history in db: "+ ex.getMessage());
        }
    }

    private GetTelegramHistoryResponse convertToTelegramHistoryResponse(TelegramHistory telegramHistory) {
        TelegramTemplate telegramTemplate = telegramHistory.getTemplate();
        return GetTelegramHistoryResponse.builder()
                .historyId(telegramHistory.getHistoryId())
                .templateId(telegramTemplate.getTelegramTemplateId())
                .templateName(telegramTemplate.getTemplateName())
                .status(telegramHistory.getStatus())
                .message(telegramTemplate.getMessage())
                .createdBy(telegramTemplate.getCreatedBy())
                .createdDate(telegramTemplate.getCreatedDate())
                .build();
    }
}
