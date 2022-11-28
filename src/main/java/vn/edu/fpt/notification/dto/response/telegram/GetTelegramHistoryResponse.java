package vn.edu.fpt.notification.dto.response.telegram;

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
 * @created : 25/10/2022 - 21:06
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetTelegramHistoryResponse implements Serializable {

    private static final long serialVersionUID = 5319677598993256911L;
    private String historyId;
    private String templateId;
    private String templateName;
    private String message;
    private String status;
    private String createdBy;
    private LocalDateTime createdDate;
}
