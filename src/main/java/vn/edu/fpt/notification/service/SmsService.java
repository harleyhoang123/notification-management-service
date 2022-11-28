package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.common.PageableResponse;
import vn.edu.fpt.notification.dto.request.sms.*;
import vn.edu.fpt.notification.dto.response.sms.CreateSmsTemplateResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsHistoryResponse;
import vn.edu.fpt.notification.dto.response.sms.GetSmsTemplateResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 10:07
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface SmsService {

    void sendSms(String templateId, SendSmsRequest request);
    void sendSms(String value);

    void registerPhoneNumberReceivedSms(RegisterPhoneNumberReceivedSmsRequest request);

    void verifyPhoneNumberCode(VerifyPhoneNumberOtpRequest request);

    CreateSmsTemplateResponse createSmsTemplate(CreateSmsTemplateRequest request);

    void updateSmsTemplate(String templateId, UpdateSmsTemplateRequest request);

    void deleteSmsTemplate(String templateId);

    PageableResponse<GetSmsTemplateResponse> getSmsTemplate(GetSmsTemplateRequest request);
    PageableResponse<GetSmsHistoryResponse> getSmsHistory(GetSmsHistoryRequest request);

}
