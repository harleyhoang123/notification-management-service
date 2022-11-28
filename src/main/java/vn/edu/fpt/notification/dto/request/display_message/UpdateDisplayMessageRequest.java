package vn.edu.fpt.notification.dto.request.display_message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 19:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateDisplayMessageRequest implements Serializable {

    private static final long serialVersionUID = 635189154108296133L;
    private String code;
    private String language;
    private String message;
}
