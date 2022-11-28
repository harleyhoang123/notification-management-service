package vn.edu.fpt.notification.dto.request.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.notification.constant.ApplicationStatus;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 13:35
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateSmsTemplateStatusRequest implements Serializable {

    private static final long serialVersionUID = -463737747425972124L;
    private ApplicationStatus status;
}
