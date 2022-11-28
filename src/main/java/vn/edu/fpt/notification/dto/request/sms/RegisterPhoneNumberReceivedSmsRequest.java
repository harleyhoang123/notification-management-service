package vn.edu.fpt.notification.dto.request.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 13:21
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterPhoneNumberReceivedSmsRequest implements Serializable {

    private static final long serialVersionUID = 1198080686328222716L;
    private String phoneNumber;
}
