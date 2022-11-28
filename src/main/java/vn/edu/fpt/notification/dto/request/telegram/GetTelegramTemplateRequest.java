package vn.edu.fpt.notification.dto.request.telegram;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

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
public class GetTelegramTemplateRequest extends AuditableRequest {

    private static final long serialVersionUID = -8585976407463502974L;
    private String templateId;
    private String templateName;
    private String message;
    private String chatId;
    private String channelName;

    public String getTemplateId() {
        return RequestDataUtils.convertSearchableData(templateId);
    }

    public String getTemplateName() {
        return RequestDataUtils.convertSearchableData(templateName);
    }

    public String getMessage() {
        return RequestDataUtils.convertSearchableData(message);
    }

    public String getChatId() {
        return RequestDataUtils.convertSearchableData(chatId);
    }

    public String getChannelName() {
        return RequestDataUtils.convertSearchableData(channelName);
    }
}
