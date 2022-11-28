package vn.edu.fpt.notification.dto.response.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 13:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetSmsHistoryResponse implements Serializable {

    private static final long serialVersionUID = -7321998320048110756L;
    private String smsHistoryId;
    private String templateId;
    private String templateName;
    private String message;
    private String status;
    private String createdBy;
    private LocalDateTime createdDate;
}
