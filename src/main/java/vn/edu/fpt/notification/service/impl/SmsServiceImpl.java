package vn.edu.fpt.notification.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.config.security.annotation.IsAdmin;
import vn.edu.fpt.notification.constant.AppConstant;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.UserInfoResponse;
import vn.edu.fpt.notification.dto.event.SendSmsEvent;
import vn.edu.fpt.notification.dto.request.sms.*;
import vn.edu.fpt.notification.dto.response.sms.CreateSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsHistoryResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsTemplateResponse;
import vn.edu.fpt.notification.entity.EmailHistory;
import vn.edu.fpt.notification.entity.SmsHistory;
import vn.edu.fpt.notification.entity.SmsTemplate;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.SmsHistoryRepository;
import vn.edu.fpt.notification.repository.SmsTemplateRepository;
import vn.edu.fpt.notification.service.SmsService;
import vn.edu.fpt.notification.service.UserInfoService;
import vn.edu.fpt.notification.utils.ParamUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:14
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SmsTemplateRepository smsTemplateRepository;
    private final SmsHistoryRepository smsHistoryRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;
    private final UserInfoService userInfoService;

    @Override
    public void sendSms(String templateId, SendSmsRequest request) {
        SmsTemplate smsTemplate = smsTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not exist"));

        String message = ParamUtils.replaceParams(smsTemplate.getMessage(), request.getParams());

        SmsHistory smsHistory = new SmsHistory();
        smsHistory.setTemplate(smsTemplate);
        smsHistory.setMessage(message);
        smsHistory.setSendTo(request.getSendTo());
        smsHistory.setParams(request.getParams());

        try {
            Message twilioMessage = Message.creator(
                            new com.twilio.type.PhoneNumber(request.getSendTo()),
                            new com.twilio.type.PhoneNumber("+15084069533"),
                            message)
                    .create();
            log.info("Send sms {} with sid: {}", twilioMessage.getStatus(), twilioMessage.getSid());
            smsHistory.setStatus(AppConstant.SUCCESS);
        } catch (Exception ex) {
            smsHistory.setStatus(AppConstant.FAILED);
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't send SMS to: " + request.getSendTo());
        }finally {
            try {
                smsHistoryRepository.save(smsHistory);
            }catch (Exception ex){
                log.error("Can't save sms history to db: "+ ex.getMessage());
            }
        }
    }

    @Override
    public void sendSms(String value) {
        try {
            SendSmsEvent sendSmsEvent = objectMapper.readValue(value, SendSmsEvent.class);
            this.sendSms(sendSmsEvent.getTemplateId(), SendSmsRequest.builder()
                            .sendTo(sendSmsEvent.getSendTo())
                            .params(sendSmsEvent.getParams())
                    .build());
        } catch (JsonProcessingException ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't convert value from flab.send_sms topics: "+ ex.getMessage());
        }
    }

    @Override
    public void registerPhoneNumberReceivedSms(RegisterPhoneNumberReceivedSmsRequest request) {

    }

    @Override
    public void verifyPhoneNumberCode(VerifyPhoneNumberOtpRequest request) {

    }

    @Override
    public CreateSmsTemplateResponse createSmsTemplate(CreateSmsTemplateRequest request) {
        SmsTemplate smsTemplate = SmsTemplate.builder()
                .templateName(request.getTemplateName())
                .message(request.getMessage())
                .params(request.getParams())
                .build();
        try {
            smsTemplate = smsTemplateRepository.save(smsTemplate);
            return CreateSmsTemplateResponse.builder()
                    .templateId(smsTemplate.getSmsTemplateId())
                    .build();
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't save sms template to db: "+ ex.getMessage());
        }
    }

    @Override
    public void updateSmsTemplate(String templateId, UpdateSmsTemplateRequest request) {
        SmsTemplate smsTemplate = smsTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not found"));
        if(Objects.nonNull(request.getTemplateName())){
            smsTemplate.setTemplateName(smsTemplate.getTemplateName());
        }
        if(Objects.nonNull(request.getMessage())){
            smsTemplate.setMessage(request.getMessage());
        }
        if(Objects.nonNull(request.getParams())){
            smsTemplate.setParams(request.getParams());
        }
        try {
            smsTemplateRepository.save(smsTemplate);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update sms template in db: "+ ex.getMessage());
        }
    }

    @Override
    @IsAdmin
    public void deleteSmsTemplate(String templateId) {
        SmsTemplate smsTemplate = smsTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not found"));
        try {
            smsTemplateRepository.delete(smsTemplate);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't delete sms template in db: "+ ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetSmsTemplateResponse> getSmsTemplate(GetSmsTemplateRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getTemplateId())){
            query.addCriteria(Criteria.where("_id").regex(request.getTemplateId()));
        }
        if(Objects.nonNull(request.getTemplateName())){
            query.addCriteria(Criteria.where("template_name").regex(request.getTemplateName()));
        }
        if(Objects.nonNull(request.getMessage())){
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, SmsTemplate.class);

        BaseMongoRepository.addCriteriaWithSorted(query, request);
        BaseMongoRepository.addCriteriaWithPageable(query, request);

        try{
            List<SmsTemplate> smsTemplates = mongoTemplate.find(query, SmsTemplate.class);
            List<GetSmsTemplateResponse> smsTemplateResponses = smsTemplates.stream().map(this::convertToGetSmsTemplateResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, smsTemplateResponses);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't query sms template in db: "+ ex.getMessage());
        }
    }

    private GetSmsTemplateResponse convertToGetSmsTemplateResponse(SmsTemplate smsTemplate) {
        return GetSmsTemplateResponse.builder()
                .templateId(smsTemplate.getSmsTemplateId())
                .templateName(smsTemplate.getTemplateName())
                .message(smsTemplate.getMessage())
                .params(smsTemplate.getParams())
                .createdBy(UserInfoResponse.builder()
                        .accountId(smsTemplate.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(smsTemplate.getCreatedBy()))
                        .build())
                .createdDate(smsTemplate.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(smsTemplate.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(smsTemplate.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(smsTemplate.getLastModifiedDate())
                .build();
    }

    @Override
    public PageableResponse<GetSmsHistoryResponse> getSmsHistory(GetSmsHistoryRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getTemplateId())){
            query.addCriteria(Criteria.where("_id").regex(request.getHistoryId()));
        }
        if(Objects.nonNull(request.getTemplateId())){
            query.addCriteria(Criteria.where("template.$_id").regex(request.getTemplateName()));
        }
        if(Objects.nonNull(request.getTemplateName())){
            query.addCriteria(Criteria.where("template.$template_name").regex(request.getTemplateName()));
        }
        if(Objects.nonNull(request.getMessage())){
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        if(Objects.nonNull(request.getStatus())){
            query.addCriteria(Criteria.where("status").regex(request.getStatus()));
        }
        if(Objects.nonNull(request.getCreatedBy())){
            query.addCriteria(Criteria.where("created_by").regex(request.getCreatedBy()));
        }
        query.addCriteria(Criteria.where("created_date").gte(request.getCreatedDateFrom()).lte(request.getCreatedDateTo()));

        Long totalElements = mongoTemplate.count(query, SmsHistory.class);

        BaseMongoRepository.addCriteriaWithSorted(query, request);
        BaseMongoRepository.addCriteriaWithPageable(query, request);

        try{
            List<SmsHistory> smsHistories = mongoTemplate.find(query, SmsHistory.class);
            List<GetSmsHistoryResponse> smsHistoryResponses = smsHistories.stream().map(this::convertToGetSmsHistoryResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, smsHistoryResponses);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't query sms history in db: "+ ex.getMessage());
        }
    }

    private GetSmsHistoryResponse convertToGetSmsHistoryResponse(SmsHistory smsHistory) {
        SmsTemplate smsTemplate = smsHistory.getTemplate();
        return GetSmsHistoryResponse.builder()
                .smsHistoryId(smsHistory.getSmsHistoryId())
                .templateId(smsTemplate.getSmsTemplateId())
                .templateName(smsTemplate.getTemplateName())
                .status(smsHistory.getStatus())
                .message(smsTemplate.getMessage())
                .createdBy(smsHistory.getCreatedBy())
                .createdDate(smsHistory.getCreatedDate())
                .build();
    }
}
