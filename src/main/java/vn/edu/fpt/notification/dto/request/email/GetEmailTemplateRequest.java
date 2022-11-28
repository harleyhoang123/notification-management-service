package vn.edu.fpt.notification.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.dto.common.PageableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 21:51
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetEmailTemplateRequest extends AuditableRequest {

    private static final long serialVersionUID = -1780292580190519623L;
    private String templateId;
    private String templateName;
    private String sendFrom;
    private String subject;
    private String message;

    public String getTemplateId() {
        return RequestDataUtils.convertSearchableData(templateId);
    }

    public String getSendFrom() {
        return RequestDataUtils.convertSearchableData(sendFrom);
    }

    public String getSubject() {
        return RequestDataUtils.convertSearchableData(subject);
    }

    public String getTemplateName() {
        return RequestDataUtils.convertSearchableData(templateName);
    }

    public String getMessage() {
        return RequestDataUtils.convertSearchableData(message);
    }
}
