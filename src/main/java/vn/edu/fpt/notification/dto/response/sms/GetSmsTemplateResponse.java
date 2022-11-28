package vn.edu.fpt.notification.dto.response.sms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;

import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 13:44
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class GetSmsTemplateResponse extends AuditableResponse {

    private static final long serialVersionUID = -1920119055900710388L;
    private String templateId;
    private String templateName;
    private String message;
    private Map<String, String> params;
}
