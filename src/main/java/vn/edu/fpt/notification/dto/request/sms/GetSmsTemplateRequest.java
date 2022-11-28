package vn.edu.fpt.notification.dto.request.sms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.entity.common.Auditor;
import vn.edu.fpt.notification.utils.RequestDataUtils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 21:45
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetSmsTemplateRequest extends AuditableRequest {

    private static final long serialVersionUID = 3182198956069151401L;
    private String templateId;
    private String templateName;
    private String message;

    public String getTemplateId() {
        return RequestDataUtils.convertSearchableData(templateId);
    }

    public String getTemplateName() {
        return RequestDataUtils.convertSearchableData(templateName);
    }

    public String getMessage() {
        return RequestDataUtils.convertSearchableData(message);
    }
}
