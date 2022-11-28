package vn.edu.fpt.notification.dto.request.sms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.PageableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 22:40
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetSmsHistoryRequest extends PageableRequest implements Serializable {

    private static final long serialVersionUID = 6466433118638218841L;
    private String historyId;
    private String templateId;
    private String templateName;
    private String message;
    private String status;

    private String createdBy;
    private String createdDateTo;
    private String createdDateFrom;

    public String getHistoryId() {
        return RequestDataUtils.convertSearchableData(historyId);
    }

    public String getTemplateId() {
        return RequestDataUtils.convertSearchableData(templateId);
    }

    public String getTemplateName() {
        return RequestDataUtils.convertSearchableData(templateName);
    }

    public String getMessage() {
        return RequestDataUtils.convertSearchableData(message);
    }

    public String getStatus() {
        return RequestDataUtils.convertSearchableData(status);
    }

    public String getCreatedBy() {
        return RequestDataUtils.convertSearchableData(createdBy);
    }

    public LocalDateTime getCreatedDateFrom() {
        return RequestDataUtils.convertDateFrom(createdDateFrom);
    }

    public LocalDateTime getCreatedDateTo() {
        return RequestDataUtils.convertDateTo(createdDateTo);
    }
}
