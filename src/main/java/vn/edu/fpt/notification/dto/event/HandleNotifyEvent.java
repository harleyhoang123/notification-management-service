package vn.edu.fpt.notification.dto.event;

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
 * @created : 23/12/2022 - 14:13
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HandleNotifyEvent implements Serializable {

    private static final long serialVersionUID = 5702313987085696286L;
    private String accountId;
    private String content;
    private LocalDateTime createdDate;
}
