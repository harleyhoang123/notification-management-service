package vn.edu.fpt.notification.dto.request.notification;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 23/12/2022 - 14:19
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"notifyId", "accountId", "contents"})
public class GetNotifyResponse implements Serializable {

    private static final long serialVersionUID = -978269329747971071L;
    private String notifyId;
    private String accountId;
    private List<NotifyContentResponse> contents;
}
