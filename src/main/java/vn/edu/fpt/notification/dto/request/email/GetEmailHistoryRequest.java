package vn.edu.fpt.notification.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.PageableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

import java.time.LocalDateTime;

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
public class GetEmailHistoryRequest extends PageableRequest {

    private static final long serialVersionUID = -5455592805095253334L;
    private String historyId;
    private String templateId;
    private String templateName;
    private String sendTo;
    private String status;
    private String createdBy;
    private String createdDateFrom;
    private String createdDateTo;

    public String getHistoryId() {
        return RequestDataUtils.convertSearchableData(historyId);
    }

    public String getTemplateId() {
        return RequestDataUtils.convertSearchableData(templateId);
    }

    public String getTemplateName() {
        return RequestDataUtils.convertSearchableData(templateName);
    }

    public String getSendTo() {
        return RequestDataUtils.convertSearchableData(sendTo);
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
