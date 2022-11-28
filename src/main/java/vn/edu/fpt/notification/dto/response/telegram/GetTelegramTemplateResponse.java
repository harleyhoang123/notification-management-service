package vn.edu.fpt.notification.dto.response.telegram;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;

import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 12:50
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetTelegramTemplateResponse extends AuditableResponse {

    private static final long serialVersionUID = -2950405685273318947L;
    private String templateId;
    private String templateName;
    private String channelId;
    private String message;
    private Map<String, String> params;

}
