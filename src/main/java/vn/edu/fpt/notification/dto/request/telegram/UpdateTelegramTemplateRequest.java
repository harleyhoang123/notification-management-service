package vn.edu.fpt.notification.dto.request.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 21:04
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateTelegramTemplateRequest implements Serializable {

    private static final long serialVersionUID = 7456887086354825824L;
    private String templateName;
    private String message;
    private String chatId;
    private String channelName;
    private Map<String, String> params;
}
