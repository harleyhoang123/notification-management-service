package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.email.*;
import vn.edu.fpt.notification.dto.response.email.CreateEmailTemplateResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailHistoryResponse;
import vn.edu.fpt.notification.dto.response.email.GetEmailTemplateResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 14:35
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface EmailService {

    void sendEmail(String templateId, SendEmailRequest request);

    void sendEmail(String value);

    CreateEmailTemplateResponse createEmailTemplate(CreateEmailTemplateRequest request);

    void updateEmailTemplate(String templateId, UpdateEmailTemplateRequest request);

    void deleteEmailTemplate(String templateId);

    PageableResponse<GetEmailHistoryResponse> getEmailHistory(GetEmailHistoryRequest request);

    PageableResponse<GetEmailTemplateResponse> getEmailTemplate(GetEmailTemplateRequest request);

    void downloadTemplateAttachFile(String fileKey, HttpServletResponse response);

}
