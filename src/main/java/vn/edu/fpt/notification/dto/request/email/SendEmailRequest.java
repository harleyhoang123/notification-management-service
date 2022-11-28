package vn.edu.fpt.notification.dto.request.email;

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
 * @created : 04/09/2022 - 01:16
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendEmailRequest implements Serializable {

    private static final long serialVersionUID = -4734269467617915396L;
    private String sendTo;
    private String bcc;
    private String cc;
    private Map<String, String> params;
}
