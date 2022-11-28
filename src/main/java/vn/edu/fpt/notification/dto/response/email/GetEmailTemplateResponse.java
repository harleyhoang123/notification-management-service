package vn.edu.fpt.notification.dto.response.email;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;

import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:30
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class GetEmailTemplateResponse  extends AuditableResponse {

    private static final long serialVersionUID = 6287212229422294851L;
    private String templateId;
    private String templateName;
    private String subject;
    private String message;
    private Map<String, String> params;
}
