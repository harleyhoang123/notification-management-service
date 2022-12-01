package vn.edu.fpt.notification.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.config.security.annotation.IsAdmin;
import vn.edu.fpt.notification.config.security.annotation.IsManager;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.display_message.CreateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.GetDisplayMessageRequest;
import vn.edu.fpt.notification.dto.request.display_message.UpdateDisplayMessageRequest;
import vn.edu.fpt.notification.dto.response.display_message.CreateDisplayMessageResponse;
import vn.edu.fpt.notification.dto.response.display_message.GetDisplayMessageResponse;
import vn.edu.fpt.notification.entity.DisplayMessage;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.mapper.DisplayMessageMapper;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.DisplayMessageRepository;
import vn.edu.fpt.notification.service.DisplayMessageService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 18:03
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "displayMessage")
public class DisplayMessageServiceImpl implements DisplayMessageService {

    private final DisplayMessageRepository displayMessageRepository;
    private final MongoTemplate mongoTemplate;
    private final DisplayMessageMapper displayMessageMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Cacheable
    public String getDisplayMessage(String code) {
        return getDisplayMessage(code, LocaleContextHolder.getLocale().getLanguage());
    }

    @Override
    @Cacheable
    public String getDisplayMessage(String code, String language) {
        DisplayMessage displayMessage = displayMessageRepository.findByCodeAndLanguage(code, language)
                .orElse(null);
        return displayMessage == null
                ? null
                : displayMessage.getMessage();
    }

    @Override
    @IsAdmin
    public CreateDisplayMessageResponse createDisplayMessage(CreateDisplayMessageRequest request) {
        Optional<DisplayMessage> displayMessageInDatabase = displayMessageRepository.findByCodeAndLanguage(request.getCode(), request.getLanguage());
        if(displayMessageInDatabase.isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Display message already exist in database");
        }

        DisplayMessage displayMessage = DisplayMessage.builder()
                .code(request.getCode())
                .language(request.getLanguage())
                .message(request.getMessage())
                .build();
        try {
            displayMessage = displayMessageRepository.save(displayMessage);
            log.info("Create display message success: {}", displayMessage);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't save new display message to database: "+ ex.getMessage());
        }

        publishDisplayMessageToRedis(displayMessage);

        return CreateDisplayMessageResponse.builder()
                .displayMessageId(displayMessage.getDisplayMessageId())
                .build();
    }

    @Override
    @IsAdmin
    public void updateDisplayMessage(String displayMessageId, UpdateDisplayMessageRequest request) {
        DisplayMessage displayMessage = displayMessageRepository.findById(displayMessageId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Display message not found"));

        if(Objects.nonNull(request.getCode())){
            log.info("Update display message code: {}", request.getCode());
            displayMessage.setCode(request.getCode());
        }
        if(Objects.nonNull(request.getLanguage())){
            log.info("Update display message language: {}", request.getLanguage());
            displayMessage.setLanguage(request.getLanguage());
        }
        if(Objects.nonNull(request.getMessage())){
            log.info("Update display message message: {}", request.getMessage());
            displayMessage.setMessage(request.getMessage());
        }

        try {
            displayMessage = displayMessageRepository.save(displayMessage);
            log.info("Update display message success");
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update display message in database: {}"+ ex.getMessage());
        }
        publishDisplayMessageToRedis(displayMessage);
    }

    private void publishDisplayMessageToRedis(DisplayMessage displayMessage){
        try{
            redisTemplate.opsForValue().set(String.format("%s:%s", displayMessage.getCode(), displayMessage.getLanguage()), objectMapper.writeValueAsString(displayMessage));
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't save new display message to redis: "+ ex.getMessage());
        }
    }

    private void deleteDisplayMessageFromRedis(DisplayMessage displayMessage){
        try {
            redisTemplate.delete(String.format("%s:%s", displayMessage.getCode(), displayMessage.getLanguage()));
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't delete display message from redis: "+ ex.getMessage());
        }
    }
    @Override
    @IsAdmin
    public void deleteDisplayMessage(String displayMessageId) {
        DisplayMessage displayMessage = displayMessageRepository.findById(displayMessageId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Display message id not found"));

        try {
            displayMessageRepository.delete(displayMessage);
            log.info("Delete display message success");
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't delete display message in database: "+ ex.getMessage());
        }
        deleteDisplayMessageFromRedis(displayMessage);
    }

    @Override
    @IsManager
    public PageableResponse<GetDisplayMessageResponse> getDisplayMessageByCondition(GetDisplayMessageRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getDisplayMessageId())){
            query.addCriteria(Criteria.where("_id").regex(request.getDisplayMessageId()));
        }
        if(Objects.nonNull(request.getCode())){
            query.addCriteria(Criteria.where("code").regex(request.getCode()));
        }
        if(Objects.nonNull(request.getLanguage())){
            query.addCriteria(Criteria.where("language").regex(request.getLanguage()));
        }
        if(Objects.nonNull(request.getMessage())){
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, DisplayMessage.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);
        try{
            List<DisplayMessage> displayMessages = mongoTemplate.find(query, DisplayMessage.class);
            List<GetDisplayMessageResponse> displayMessageResponses = displayMessages.stream().map(displayMessageMapper::mapToGetDisplayMessageResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, displayMessageResponses);
        }catch (Exception ex){
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find display message in db: "+ ex.getMessage());
        }

    }

}
