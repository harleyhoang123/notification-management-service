package vn.edu.fpt.notification.dto.response.display_message;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 19:43
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class GetDisplayMessageResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = 3800351454255454940L;
    private String displayMessageId;
    private String code;
    private String language;
    private String message;
}
