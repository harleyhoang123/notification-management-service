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
 * @created : 25/10/2022 - 21:03
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateTelegramTemplateRequest implements Serializable {

    private static final long serialVersionUID = 2568035450800269894L;
    private String templateName;
    private String message;
    private Map<String, String> params;
    private String chatId;
    private String channelName;

}
