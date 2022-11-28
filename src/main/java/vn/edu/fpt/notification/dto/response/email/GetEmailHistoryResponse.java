package vn.edu.fpt.notification.dto.response.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:50
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetEmailHistoryResponse implements Serializable {

    private static final long serialVersionUID = -3794139425415203149L;
    private String historyId;
    private String templateId;
    private String templateName;
    private String sendTo;
    private String cc;
    private String bcc;
    private String status;
    private String subject;
    private String message;
    private String createdBy;
    private LocalDateTime createdDate;
}
