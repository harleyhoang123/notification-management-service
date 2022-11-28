package vn.edu.fpt.notification.dto.request.display_message;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 19:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder(toBuilder=true)
public class GetDisplayMessageRequest extends AuditableRequest implements Serializable {

    private static final long serialVersionUID = 3750804939653935404L;
    private String displayMessageId;
    private String code;
    private String language;
    private String message;

    public String getDisplayMessageId() {
        return RequestDataUtils.convertSearchableData(displayMessageId);
    }

    public String getCode() {
        return RequestDataUtils.convertSearchableData(code);
    }

    public String getLanguage() {
        return RequestDataUtils.convertSearchableData(language);
    }

    public String getMessage() {
        return RequestDataUtils.convertSearchableData(message);
    }
}
