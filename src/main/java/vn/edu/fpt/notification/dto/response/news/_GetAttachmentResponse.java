package vn.edu.fpt.notification.dto.response.news;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/12/2022 - 07:30
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonPropertyOrder({"attachmentId", "URL"})
public class _GetAttachmentResponse implements Serializable {

    private static final long serialVersionUID = 1734380326513360624L;
    private String attachmentId;
    private String URL;
}
