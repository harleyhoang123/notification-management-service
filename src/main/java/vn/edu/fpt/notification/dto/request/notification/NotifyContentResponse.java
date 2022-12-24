package vn.edu.fpt.notification.dto.request.notification;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
 * @created : 23/12/2022 - 14:21
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"content", "createdDate"})
public class NotifyContentResponse implements Serializable {

    private static final long serialVersionUID = -2135306334320965034L;
    private String content;
    private LocalDateTime createdDate;
}
