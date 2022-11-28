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
 * @created : 07/09/2022 - 13:22
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VerifyPhoneNumberOtpRequest implements Serializable {

    private static final long serialVersionUID = -8886152635423561731L;
    private String phoneNumber;
    private String code;
}
