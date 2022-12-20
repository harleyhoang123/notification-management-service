package vn.edu.fpt.notification.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.notification.config.security.annotation.IsAdmin;
import vn.edu.fpt.notification.constant.AppConstant;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.common.UserInfoResponse;
import vn.edu.fpt.notification.dto.event.SendEmailEvent;
import vn.edu.fpt.notification.dto.request.email.*;
import vn.edu.fpt.notification.dto.response.email.CreateEmailTemplateResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailHistoryResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailTemplateResponse;
import vn.edu.fpt.notification.entity.AttachFile;
import vn.edu.fpt.notification.entity.EmailHistory;
import vn.edu.fpt.notification.entity.EmailTemplate;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.BaseMongoRepository;
import vn.edu.fpt.notification.repository.EmailHistoryRepository;
import vn.edu.fpt.notification.repository.EmailTemplateRepository;
import vn.edu.fpt.notification.service.EmailService;
import vn.edu.fpt.notification.service.S3BucketStorageService;
import vn.edu.fpt.notification.service.UserInfoService;
import vn.edu.fpt.notification.utils.ParamUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 14:36
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailHistoryRepository emailHistoryRepository;
    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;
    private final UserInfoService userInfoService;
    private final S3BucketStorageService s3BucketStorageService;
    private final MongoTemplate mongoTemplate;

    @Value("${app.email.default}")
    private String defaultEmail;

    @Override
    public void sendEmail(String templateId, SendEmailRequest request) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not found"));
        EmailHistory emailHistory = new EmailHistory();
        emailHistory.setTemplate(emailTemplate);
        emailHistory.setSendTo(request.getSendTo());
        emailHistory.setCc(request.getCc());
        emailHistory.setBcc(request.getBcc());
        emailHistory.setParams(request.getParams());

        String body = ParamUtils.replaceParams(emailTemplate.getMessage(), request.getParams());
        emailHistory.setMessage(body);

        if (emailTemplate.getAttachFiles().isEmpty()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(request.getSendTo());
//            simpleMailMessage.setCc(request.getCc());
//            simpleMailMessage.setBcc(request.getBcc());
            simpleMailMessage.setFrom(defaultEmail);
            simpleMailMessage.setSubject(emailTemplate.getSubject());
            simpleMailMessage.setText(body);

            try {
                javaMailSender.send(simpleMailMessage);
                log.info("Send email to: {} success", request.getSendTo());
                emailHistory.setStatus(AppConstant.SUCCESS);
            } catch (Exception ex) {
                emailHistory.setStatus(AppConstant.FAILED);
                throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't send email: " + ex.getMessage());
            } finally {
                try {
                    emailHistoryRepository.save(emailHistory);
                } catch (Exception ex) {
                    log.error("Can't save email history to db: " + ex.getMessage());
                }
            }
        } else {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setCc(request.getCc());
                mimeMessageHelper.setBcc(request.getBcc());
                mimeMessageHelper.setFrom(emailTemplate.getSendFrom());
                mimeMessageHelper.setTo(request.getSendTo());
                mimeMessageHelper.setSubject(emailTemplate.getSubject());
                mimeMessageHelper.setText(body);
                List<AttachFile> attachFiles = emailTemplate.getAttachFiles();
                for (int i = 0; i < attachFiles.size(); i++) {
                    mimeMessageHelper.addAttachment(attachFiles.get(i).getFileName(), s3BucketStorageService.downloadFile(attachFiles.get(i).getFileKey()));
                }
                javaMailSender.send(mimeMessage);
                emailHistory.setStatus(AppConstant.SUCCESS);
            } catch (MessagingException ex) {
                emailHistory.setStatus(AppConstant.FAILED);
                throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't send mime message: " + ex.getMessage());
            } finally {
                try {
                    emailHistoryRepository.save(emailHistory);
                } catch (Exception ex) {
                    log.error("Can't save email history to db: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public void sendEmail(String value) {
        try {
            SendEmailEvent sendEmailEvent = objectMapper.readValue(value, SendEmailEvent.class);
            SendEmailRequest request = SendEmailRequest.builder()
                    .sendTo(sendEmailEvent.getSendTo())
                    .bcc(sendEmailEvent.getBcc())
                    .cc(sendEmailEvent.getCc())
                    .params(sendEmailEvent.getParams())
                    .build();
            this.sendEmail(sendEmailEvent.getTemplateId(), request);
        } catch (JsonProcessingException ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't convert value from flab.send_email topics: " + ex.getMessage());
        }
    }

    @Override
    public CreateEmailTemplateResponse createEmailTemplate(CreateEmailTemplateRequest request) {
        Optional<EmailTemplate> emailTemplateInDatabase = emailTemplateRepository.findByTemplateName(request.getTemplateName());
        if (emailTemplateInDatabase.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Email template already in database");
        }
        List<AttachFile> attachFiles = new ArrayList<>();

        MultipartFile[] files = request.getAttachFile();
        if (Objects.nonNull(files)) {
            for (int i = 0; i < files.length; i++) {
                attachFiles.add(new AttachFile(files[i].getOriginalFilename(), s3BucketStorageService.uploadFile(files[i])));
            }
        }

        EmailTemplate emailTemplate = EmailTemplate.builder()
                .templateName(request.getTemplateName())
                .sendFrom(request.getSendFrom())
                .message(request.getMessage())
                .subject(request.getSubject())
                .params(request.getParams())
                .attachFiles(attachFiles)
                .build();
        try {
            emailTemplate = emailTemplateRepository.save(emailTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't save new email template to database: " + ex.getMessage());
        }
        return CreateEmailTemplateResponse.builder()
                .templateId(emailTemplate.getEmailTemplateId())
                .build();
    }

    @Override
    public void updateEmailTemplate(String templateId, UpdateEmailTemplateRequest request) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not found"));
        if (Objects.nonNull(request.getTemplateName())) {
            emailTemplate.setTemplateName(request.getTemplateName());
        }
        if (Objects.nonNull(request.getMessage())) {
            emailTemplate.setMessage(request.getMessage());
        }
        if (Objects.nonNull(request.getParams())) {
            emailTemplate.setParams(request.getParams());
        }

        try {
            emailTemplateRepository.save(emailTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update email template in database: " + ex.getMessage());
        }
    }

    @Override
    @IsAdmin
    public void deleteEmailTemplate(String templateId) {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Template id not found"));
        try {
            emailTemplateRepository.delete(emailTemplate);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't delete email template in database: " + ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetEmailHistoryResponse> getEmailHistory(GetEmailHistoryRequest request) {
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
        if (Objects.nonNull(request.getSendTo())) {
            query.addCriteria(Criteria.where("send_to").regex(request.getSendTo()));
        }
        if (Objects.nonNull(request.getStatus())) {
            query.addCriteria(Criteria.where("status").regex(request.getStatus()));
        }
        if (Objects.nonNull(request.getCreatedBy())) {
            query.addCriteria(Criteria.where("created_by").regex(request.getCreatedBy()));
        }
        query.addCriteria(Criteria.where("created_date").gte(request.getCreatedDateFrom()).lte(request.getCreatedDateTo()));
        Long totalElements = mongoTemplate.count(query, EmailHistory.class);

        BaseMongoRepository.addCriteriaWithSorted(query, request);
        BaseMongoRepository.addCriteriaWithPageable(query, request);

        List<EmailHistory> emailHistories;
        try {
            emailHistories = mongoTemplate.find(query, EmailHistory.class);
            List<GetEmailHistoryResponse> emailHistoryResponses = emailHistories.stream().map(this::convertToGetEmailHistoryResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, emailHistoryResponses);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find email history by condition: " + ex.getMessage());
        }
    }

    private GetEmailHistoryResponse convertToGetEmailHistoryResponse(EmailHistory emailHistory) {
        EmailTemplate emailTemplate = emailHistory.getTemplate();
        return GetEmailHistoryResponse.builder()
                .historyId(emailHistory.getEmailHistoryId())
                .templateId(emailTemplate.getEmailTemplateId())
                .templateName(emailTemplate.getTemplateName())
                .subject(emailTemplate.getSubject())
                .cc(emailHistory.getCc())
                .bcc(emailHistory.getBcc())
                .sendTo(emailHistory.getSendTo())
                .status(emailHistory.getStatus())
                .message(emailHistory.getMessage())
                .createdBy(emailHistory.getCreatedBy())
                .createdDate(emailHistory.getCreatedDate())
                .build();
    }


    @Override
    public PageableResponse<GetEmailTemplateResponse> getEmailTemplate(GetEmailTemplateRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getTemplateId())) {
            query.addCriteria(Criteria.where("_id").regex(request.getTemplateId()));
        }
        if (Objects.nonNull(request.getTemplateName())) {
            query.addCriteria(Criteria.where("template_name").regex(request.getTemplateName()));
        }
        if (Objects.nonNull(request.getSendFrom())) {
            query.addCriteria(Criteria.where("send_from").regex(request.getSendFrom()));
        }
        if (Objects.nonNull(request.getSubject())) {
            query.addCriteria(Criteria.where("subject").regex(request.getSubject()));
        }
        if (Objects.nonNull(request.getMessage())) {
            query.addCriteria(Criteria.where("message").regex(request.getMessage()));
        }
        BaseMongoRepository.addCriteriaWithAuditable(query, request);
        Long totalElements = mongoTemplate.count(query, EmailTemplate.class);
        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<EmailTemplate> emailTemplates;
        try {
            emailTemplates = mongoTemplate.find(query, EmailTemplate.class);
            List<GetEmailTemplateResponse> getEmailTemplateResponses = emailTemplates.stream().map(this::convertToGetEmailTemplateResponse).collect(Collectors.toList());
            return new PageableResponse<>(request, totalElements, getEmailTemplateResponses);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find email template in db: " + ex.getMessage());
        }
    }

    private GetEmailTemplateResponse convertToGetEmailTemplateResponse(EmailTemplate emailTemplate) {
        return GetEmailTemplateResponse.builder()
                .templateId(emailTemplate.getEmailTemplateId())
                .templateName(emailTemplate.getTemplateName())
                .subject(emailTemplate.getSubject())
                .message(emailTemplate.getMessage())
                .params(emailTemplate.getParams())
                .createdBy(UserInfoResponse.builder()
                        .accountId(emailTemplate.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(emailTemplate.getCreatedBy()))
                        .build())
                .createdDate(emailTemplate.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(emailTemplate.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(emailTemplate.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(emailTemplate.getLastModifiedDate())
                .build();
    }

    @Override
    public void downloadTemplateAttachFile(String fileKey, HttpServletResponse response) {
        s3BucketStorageService.downloadFile(fileKey, response);
    }

}
