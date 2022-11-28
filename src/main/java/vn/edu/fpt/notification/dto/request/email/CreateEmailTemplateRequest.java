package vn.edu.fpt.notification.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 01:31
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateEmailTemplateRequest implements Serializable {

    private static final long serialVersionUID = 215157004952165585L;
    private String templateName;
    private String sendFrom;
    private String subject;
    private String message;
    private Map<String, String> params;
    private MultipartFile[] attachFile;
}
