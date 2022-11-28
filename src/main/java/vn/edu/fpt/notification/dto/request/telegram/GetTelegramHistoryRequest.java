package vn.edu.fpt.notification.dto.request.telegram;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.dto.common.PageableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 21:04
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetTelegramHistoryRequest extends PageableRequest {

    private static final long serialVersionUID = 8648961900170134554L;
    private String historyId;
    private String templateId;
    private String templateName;
    private String channelName;
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

    public String getChannelName() {
        return RequestDataUtils.convertSearchableData(channelName);
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

    public LocalDateTime getCreatedDateTo() {
        return RequestDataUtils.convertDateTo(createdDateTo);
    }

    public LocalDateTime getCreatedDateFrom() {
        return RequestDataUtils.convertDateFrom(createdDateFrom);
    }
}
